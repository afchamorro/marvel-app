package com.acoders.marvelfanbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addTestFragment()
    }

    private fun addTestFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHost, SuperheroesDetailFragment.newInstance(1009368)).commit()
    }
}
