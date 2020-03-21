package ru.tradernet.data.api.model

data class Request(
    val cmd: String,
    val params: Params
)

data class Params(
    val type: String,
    val exchange: String,
    val gainers: Int,
    val limit: Int
)

enum class Command(val commandName: String) {
    GET_TOP_SECURITIES("getTopSecurities")
}