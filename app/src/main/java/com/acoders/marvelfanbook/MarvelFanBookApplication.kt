package com.acoders.marvelfanbook

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MarvelFanBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initTimberLib()
    }

    private fun initTimberLib() {
        Timber.plant(Timber.DebugTree())
    }
}
