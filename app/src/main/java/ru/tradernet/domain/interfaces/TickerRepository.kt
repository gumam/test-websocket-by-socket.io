package ru.tradernet.domain.interfaces

interface TickerRepository {

    suspend fun createSubscription(tickersCodes: List<String>)

    fun onCreate()

    fun onDestroy()
}