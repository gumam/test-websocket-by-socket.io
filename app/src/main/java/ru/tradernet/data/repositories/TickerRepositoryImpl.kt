package ru.tradernet.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.domain.model.TickerInfoModel
import timber.log.Timber
import java.net.URISyntaxException


class TickerRepositoryImpl(
    private val moshi: Moshi
) : TickerRepository, Emitter.Listener {

    private val timber: Timber.Tree
        get() = Timber.tag("webSocket")

    private val REQUEST_UPDATE_CHANNEL = "sup_updateSecurities2"

    private val tickers = MutableLiveData<List<TickerInfoModel>>()

    private var socket: Socket? = null
    init {
        try {
            socket = IO.socket("https://ws3.tradernet.ru")
        } catch (e: URISyntaxException) {
            timber.e(e)
        }
    }

    override suspend fun createSubscription(tickersCodes: List<String>) {

        val type = Types.newParameterizedType(
            MutableList::class.java,
            String::class.java
        )
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)
        val jsonText = jsonAdapter.toJson(tickersCodes)
        socket?.emit(REQUEST_UPDATE_CHANNEL, jsonText)

        timber.d("createSubscription: $jsonText")
    }

    override suspend fun subscribeToTickersInfo(): LiveData<List<TickerInfoModel>> {
        return tickers
    }

    override fun onCreate() {
        timber.d("onCreate")

        socket?.on(REQUEST_UPDATE_CHANNEL, this)
        socket?.connect()
    }

    override fun onDestroy() {
        timber.d("onDestroy")

        socket?.disconnect()
        socket?.off(REQUEST_UPDATE_CHANNEL, this)
    }

    override fun call(vararg args: Any?) {
        val jsonText: String = args[0].toString()

        //todo parse json
        timber.d(jsonText)
    }
}