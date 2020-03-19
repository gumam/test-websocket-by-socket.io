package ru.tradernet.data.repositories

import ru.tradernet.domain.interfaces.TikerRepository
import ru.tradernet.domain.model.TikerInfoModel

class TikerRepositoryImpl : TikerRepository {
    override suspend fun fetchTikers(): List<TikerInfoModel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun fetchRepositoryData(
        ownerLogin: String,
        repoName: String,
        commitsCount: Int?
    ): TikerInfoModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}