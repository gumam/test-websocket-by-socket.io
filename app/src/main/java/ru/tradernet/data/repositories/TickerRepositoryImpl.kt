package ru.tradernet.data.repositories

import androidx.lifecycle.MutableLiveData
import okhttp3.*
import okio.ByteString
import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.domain.model.TikerInfoModel
import timber.log.Timber

class TickerRepositoryImpl(
    private val webClient: OkHttpClient
) : TickerRepository, WebSocketListener() {

    val TICKERS_NAMES =
        "RSTI,GAZP,MRKZ,RUAL,HYDR,MRKS,SBER,FEES,TGKA,VTBR,ANH.US,VICL.US,BURG.US," +
                "NBL.US,YETI.US,WSFS.US,NIO.US,DXC.US,MIC.US,HSBC.US,EXPN.EU,GSK.EU,SHP.EU,MAN.EU," +
                "DB1.EU,MUV2.EU,TATE.EU,KGF.EU,MGGT.EU,SGGD.EU"

    val tikers = MutableLiveData<TikerInfoModel>()

    init {
        openConnection()
    }

    override suspend fun fetchTikers(): List<TikerInfoModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Hello, it's Saurel !")

        Timber.tag("webSocket").d("onOpen")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Timber.tag("webSocket").d("onMessage bytes: $bytes")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Timber.tag("webSocket").d("onMessage text: $text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)

        Timber.tag("webSocket").d("onClosing")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)

        Timber.tag("webSocket").d("onClosed")
    }

    private fun openConnection() {
        webClient.newWebSocket(
            Request.Builder().url("wss://ws3.tradernet.ru").build(),
            this
        )

        Timber.tag("webSocket").d("openConnection")
    }
}