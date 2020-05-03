package me.gaojianli.yetanothertiktok.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.gaojianli.yetanothertiktok.R
import me.gaojianli.yetanothertiktok.ui.main.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }
}