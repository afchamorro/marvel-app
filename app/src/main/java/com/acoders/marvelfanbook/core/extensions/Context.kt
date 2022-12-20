package com.acoders.marvelfanbook.core.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun isSdkVersion(versionCode: Int): Boolean = (Build.VERSION.SDK_INT >= versionCode)