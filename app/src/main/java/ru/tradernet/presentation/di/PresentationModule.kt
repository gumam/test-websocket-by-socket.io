package ru.tradernet.presentation.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.tradernet.presentation.ui.mainList.MainListViewModel

object PresentationModule {

    fun create() = module {
        viewModel { MainListViewModel(get()) }
    }
}