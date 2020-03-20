package ru.tradernet.domain.interactors

import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.domain.model.TikerInfoModel

class TickerInteractor(
    private val repository: TickerRepository
) {

//    suspend fun fetchTikersInfo(): List<TikerInfoModel> {
//        return repository.fetchTikers()
//    }
}