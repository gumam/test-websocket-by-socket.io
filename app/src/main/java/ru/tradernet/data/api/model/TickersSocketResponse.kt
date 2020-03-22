package ru.tradernet.data.api.model

import ru.tradernet.domain.model.TickerInfoModel

data class TickersSocketResponse(
    val q: List<TickerInfoModel>
)