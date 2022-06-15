package com.acoders.marvelfanbook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acoders.marvelfanbook.features.superheroes.presentation.ui.SuperheroesFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
