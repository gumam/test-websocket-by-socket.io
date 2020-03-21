package ru.tradernet.domain.interactors

import ru.tradernet.data.api.Api
import ru.tradernet.data.api.model.Command
import ru.tradernet.data.api.model.Params
import ru.tradernet.data.api.model.Request
import ru.tradernet.domain.interfaces.TickerRepository

class TickerInteractor(
    private val repository: TickerRepository,
    private val api: Api
) {

    suspend fun fetchTickersCodes(): List<String> {
        return api.get(
            Request(
                command = Command.GET_TOP_SECURITIES.commandName,
                params = Params(
                    type = "stocks",
                    exchange = "russia",
                    gainers = 0,
                    limit = 20
                )
            )
        ).tickers
    }
}