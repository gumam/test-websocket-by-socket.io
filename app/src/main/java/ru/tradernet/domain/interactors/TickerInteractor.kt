package ru.tradernet.domain.interactors

import androidx.lifecycle.LiveData
import com.squareup.moshi.Moshi
import ru.tradernet.data.api.Api
import ru.tradernet.data.api.model.Command
import ru.tradernet.data.api.model.Params
import ru.tradernet.data.api.model.Request
import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.domain.model.TickerInfoModel

class TickerInteractor(
    private val repository: TickerRepository,
    private val api: Api,
    private val moshi: Moshi
) {

    suspend fun fetchTickersCodes(): List<String> {
        val request = Request(
            cmd = Command.GET_TOP_SECURITIES.commandName,
            params = Params(
                type = "stocks",
                exchange = "russia",
                gainers = 0,
                limit = 30
            )
        )
        val jsonAdapter = moshi.adapter(Request::class.java)
        val jsonText = jsonAdapter.toJson(request)
        return api.get(jsonText).tickers
    }

    suspend fun createSubscription(tickersCodes: List<String>) {
        repository.setTickersCodes(tickersCodes)
    }

    suspend fun subscribeToTickersInfo(): LiveData<List<TickerInfoModel>> {
        return repository.subscribeToTickersInfo()
    }
}