package com.acoders.marvelfanbook.core.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.setupStatusBarColor(idColor: Int) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, idColor)
}