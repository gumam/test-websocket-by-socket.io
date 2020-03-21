package ru.tradernet.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.getKoin
import ru.tradernet.R
import ru.tradernet.domain.interfaces.TickerRepository
import ru.tradernet.presentation.ui.mainList.MainListFragment

class MainActivity : AppCompatActivity() {

    val tickerRepository: TickerRepository = getKoin().get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) supportFragmentManager.beginTransaction().add(
            R.id.container,
            MainListFragment()
        ).commit()

        tickerRepository.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        tickerRepository.onDestroy()
    }
}
