package ru.tradernet.data.api.model

import com.squareup.moshi.Json

data class Request(
    @Json(name = "cmd")
    val command: String,
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