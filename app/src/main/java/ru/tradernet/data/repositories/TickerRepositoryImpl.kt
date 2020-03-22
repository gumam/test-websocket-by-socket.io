package ru.tradernet.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import org.json.JSONArray
import ru.tradernet.data.api.model.TickersSocketResponse
import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.domain.model.TickerInfoModel
import timber.log.Timber
import java.net.URISyntaxException

class TickerRepositoryImpl(
    private val moshi: Moshi,
    private val okHttpClient: OkHttpClient
) : TickerRepository {

    private val timber: Timber.Tree
        get() = Timber.tag("webSocket")

    private val defaultCodes = "RSTI,GAZP,MRKZ,RUAL,HYDR,MRKS,SBER,FEES,TGKA,VTBR,ANH.US,VICL.US," +
            "BURG.US,NBL.US,YETI.US,WSFS.US,NIO.US,DXC.US,MIC.US,HSBC.US,EXPN.EU,GSK.EU,SHP.EU," +
            "MAN.EU,DB1.EU,MUV2.EU,TATE.EU,KGF.EU,MGGT.EU,SGGD.EU"

    private var tickersCodes: List<String> = listOf()

    private var connected = false

    private val tickers = MutableLiveData<List<TickerInfoModel>>()

    private var socket: Socket? = null

    override suspend fun setTickersCodes(tickersCodes: List<String>) {
        tickers.postValue(listOf())
        this.tickersCodes = if (tickersCodes.isNotEmpty()) tickersCodes
        else defaultCodes.split(",")
        if (connected) setSubscription()
    }

    private fun setSubscription() {
        socket?.on("q") {
            val jsonText: String? = it.getOrNull(0)?.toString()
            jsonText?.run {
                val adapter = moshi.adapter(TickersSocketResponse::class.java)
                val newTickers = adapter.fromJson(this)?.q
                val currentTickers = tickers.value?.toMutableList() ?: mutableListOf()

                newTickers?.forEach { newTicker ->
                    val sameTickerIndex = currentTickers.indexOfFirst { it.name == newTicker.name }
                    val sameTicker =
                        if (sameTickerIndex >= 0) currentTickers[sameTickerIndex] else null

                    if (sameTicker == null) currentTickers.add(newTicker)
                    else currentTickers[sameTickerIndex] = sameTicker.copyFrom(newTicker)
                }

                tickers.postValue(currentTickers)
                timber.d("received q tickers")
            }
        }

        val jsonArray = JSONArray()
        tickersCodes.forEach { jsonArray.put(it) }
        socket?.emit("sup_updateSecurities2", jsonArray)
    }

    override suspend fun subscribeToTickersInfo(): LiveData<List<TickerInfoModel>> {
        return tickers
    }

    override fun onCreate() {
        timber.d("onCreate")

        initSocket()
    }

    override fun onDestroy() {
        timber.d("onDestroy")
        connected = false
        socket?.close()
        socket?.off()

        socket = null
    }

    private fun initSocket() {
        try {
            val options = IO.Options()
            options.callFactory = okHttpClient
            options.webSocketFactory = okHttpClient
            socket = IO.socket("https://ws3.tradernet.ru", options)
            socket!!.on(Socket.EVENT_DISCONNECT) {
                connected = false
                timber.d("disconnect")
            }
                .on(Socket.EVENT_CONNECT) {
                    connected = true
                    timber.d("connect")
                    setSubscription()
                }
                .on(Socket.EVENT_ERROR) {
                    val error = it.getOrNull(0) as? Exception
                    timber.w(error)
                }
                .on(Socket.EVENT_ERROR) {
                    val error = it.getOrNull(0) as? Exception
                    timber.w(error)
                }
            socket!!.open()
        } catch (e: URISyntaxException) {
            timber.e(e)
        }
    }
}