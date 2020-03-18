package ru.tradernet.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.tradernet.test.R
import ru.tradernet.presentation.ui.mainList.MainListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.container, MainListFragment()).commit()
    }
}
