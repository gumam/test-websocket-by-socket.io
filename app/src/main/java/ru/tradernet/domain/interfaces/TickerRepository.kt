package ru.tradernet.domain.interfaces

import ru.tradernet.domain.model.TikerInfoModel

interface TickerRepository {

    suspend fun fetchTikers(): List<TikerInfoModel>
}