package com.acoders.marvelfanbook.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}
