package ru.tradernet.domain.model

import com.squareup.moshi.Json

data class TickerInfoModel(
    @Json(name = "c")
    val name: String,

    @Json(name = "pcp")
    val percentChanges: Float?,

    var percentFieldChanges: Float = 0F,

    @Json(name = "ltr")
    val exchangeName: String?,

    @Json(name = "name")
    val stockName: String?,

    @Json(name = "ltp")
    val price: Float?,

    @Json(name = "chg")
    val pointChanges: Float?
) {
    fun copyFrom(newTicker: TickerInfoModel?): TickerInfoModel {
        val newPercentChanges = newTicker?.percentChanges
        val fieldPercentChange =
            if (newPercentChanges != null && percentChanges != null) newPercentChanges - percentChanges else 0F
        return this.copy(
            name = name,
            percentChanges = newTicker?.percentChanges ?: percentChanges,
            exchangeName = newTicker?.exchangeName ?: exchangeName,
            stockName = newTicker?.stockName ?: stockName,
            price = newTicker?.price ?: price,
            pointChanges = newTicker?.pointChanges ?: pointChanges,
            percentFieldChanges = fieldPercentChange
        )
    }
}