package ru.tradernet.presentation

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.tradernet.test.BuildConfig
import ru.tradernet.presentation.di.Modules
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(Modules.allModules())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}