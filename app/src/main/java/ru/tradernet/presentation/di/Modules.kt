package ru.tradernet.presentation.di

import org.koin.core.module.Module
import ru.tradernet.data.DataModule
import ru.tradernet.domain.DomainModule

object Modules {

    fun allModules(): List<Module> {
        return listOf(
            DataModule.create(),
            PresentationModule.create(),
            DomainModule.create()
        )
    }
}