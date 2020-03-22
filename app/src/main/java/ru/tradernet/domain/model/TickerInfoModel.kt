package ru.tradernet.domain.model

import com.squareup.moshi.Json

data class TickerInfoModel(
    @Json(name = "c")
    val name: String,

    @Json(name = "pcp")
    val percentChanges: Float?,

    @Json(name = "ltr")
    val exchangeName: String?,

    @Json(name = "name")
    val stockName: String?,

    @Json(name = "ltp")
    val price: Float?,

    @Json(name = "chg")
    val pointChanges: Float?
)