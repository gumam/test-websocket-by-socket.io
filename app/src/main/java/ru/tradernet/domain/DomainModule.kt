package ru.tradernet.domain

import org.koin.dsl.module
import ru.tradernet.domain.interactors.TikerInteractor

object DomainModule {

    fun create() = module {
        factory { TikerInteractor(get()) }
    }
}