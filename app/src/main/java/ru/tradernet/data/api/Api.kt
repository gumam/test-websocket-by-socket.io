package ru.tradernet.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.tradernet.data.api.model.Request
import ru.tradernet.data.api.model.TickersCodesResponse

interface Api {

    @GET
    suspend fun get(@Query("q") model: Request): TickersCodesResponse
}