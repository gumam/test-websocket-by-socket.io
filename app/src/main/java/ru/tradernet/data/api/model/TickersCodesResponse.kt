package ru.tradernet.data.api.model

data class TickersCodesResponse(
    val tickers: List<String>,
    val code: Int
)