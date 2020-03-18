package ru.tradernet.domain.interactors

import ru.tradernet.domain.interfaces.TikerRepository
import ru.tradernet.domain.model.TikerInfoModel

class TikerInteractor(
    private val repository: TikerRepository
) {

    suspend fun fetchTikersInfo(): List<TikerInfoModel> {
        return repository.fetchTikers()
    }
}