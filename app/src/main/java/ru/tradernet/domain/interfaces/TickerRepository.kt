package ru.tradernet.domain.interfaces

import androidx.lifecycle.LiveData
import ru.tradernet.domain.model.TickerInfoModel

interface TickerRepository {

    suspend fun createSubscription(tickersCodes: List<String>)

    suspend fun subscribeToTickersInfo(): LiveData<List<TickerInfoModel>>

    fun onCreate()

    fun onDestroy()
}