package ru.tradernet.domain

import org.koin.dsl.module
import ru.tradernet.domain.interactors.TickerInteractor

object DomainModule {

    fun create() = module {
        factory { TickerInteractor(get(), get()) }
    }
}