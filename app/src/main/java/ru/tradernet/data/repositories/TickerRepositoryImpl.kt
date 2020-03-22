package ru.tradernet.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import org.json.JSONArray
import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.domain.model.TickerInfoModel
import timber.log.Timber
import java.net.URISyntaxException


class TickerRepositoryImpl(
    okHttpClient: OkHttpClient
) : TickerRepository {

    private val timber: Timber.Tree
        get() = Timber.tag("webSocket")

    private val defaultCodes = "RSTI,GAZP,MRKZ,RUAL,HYDR,MRKS,SBER,FEES,TGKA,VTBR,ANH.US,VICL.US," +
            "BURG.US,NBL.US,YETI.US,WSFS.US,NIO.US,DXC.US,MIC.US,HSBC.US,EXPN.EU,GSK.EU,SHP.EU," +
            "MAN.EU,DB1.EU,MUV2.EU,TATE.EU,KGF.EU,MGGT.EU,SGGD.EU"

    private var tickersCodes: List<String> = defaultCodes.split(',')

    private var connected = false

    private val tickers = MutableLiveData<List<TickerInfoModel>>()

    private var socket: Socket? = null

    init {
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
                .on(Socket.EVENT_MESSAGE) {
                    timber.d("message")
                }
                .on(Socket.EVENT_ERROR) {
                    val error = it.getOrNull(0) as? Exception
                    timber.e(error)
                }
                .on(Socket.EVENT_ERROR) {
                    val error = it.getOrNull(0) as? Exception
                    timber.e(error)
                }
                .on(Socket.EVENT_PING) {
                    timber.d("ping")
                }
                .on(Socket.EVENT_PONG) {
                    timber.d("pong")
                }
        } catch (e: URISyntaxException) {
            timber.e(e)
        }
    }

    override suspend fun setTickersCodes(tickersCodes: List<String>) {
        if (tickersCodes.isNotEmpty()) this.tickersCodes = tickersCodes
        if (connected) setSubscription()
    }

    private fun setSubscription() {
        socket?.on("q") {
            val jsonText: String = it.getOrNull(0).toString()

            //todo parse json
            timber.d("q: $jsonText")
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

        socket?.open()
    }

    override fun onDestroy() {
        timber.d("onDestroy")

        socket?.close()
        socket?.off()
    }
}