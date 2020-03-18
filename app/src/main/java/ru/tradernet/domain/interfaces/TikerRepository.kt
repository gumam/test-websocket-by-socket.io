package ru.tradernet.domain.interfaces

import ru.tradernet.domain.model.TikerInfoModel

interface TikerRepository {

    suspend fun fetchTikers(): List<TikerInfoModel>

    suspend fun fetchCommitsCount(ownerLogin: String, repoName: String): Int

    suspend fun fetchRepositoryData(
        ownerLogin: String,
        repoName: String,
        commitsCount: Int? = null
    ): TikerInfoModel
}