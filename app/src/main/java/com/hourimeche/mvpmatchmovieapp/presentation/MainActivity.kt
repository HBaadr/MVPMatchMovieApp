package com.hourimeche.mvpmatchmovieapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hourimeche.mvpmatchmovieapp.R
import com.hourimeche.mvpmatchmovieapp.presentation.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}