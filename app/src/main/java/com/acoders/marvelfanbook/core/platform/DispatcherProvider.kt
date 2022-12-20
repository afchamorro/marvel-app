package com.acoders.marvelfanbook.core.platform

import kotlinx.coroutines.Dispatchers

interface DispatcherProvider {

    val main
        get() = Dispatchers.Main

    val io
        get() = Dispatchers.IO

    val default
        get() = Dispatchers.Default

    val unconfined
        get() = Dispatchers.Unconfined
}

class AppDispatcherProvider : DispatcherProvider