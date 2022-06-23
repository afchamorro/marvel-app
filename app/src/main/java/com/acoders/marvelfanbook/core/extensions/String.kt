package com.acoders.marvelfanbook.core.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spanned
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest

private const val MD5_RADIX = 16

fun String.md5(length: Int = 32): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(MD5_RADIX).padStart(length, '0')

fun String.Companion.empty() = ""

fun String.logI(t: Throwable? = null) = Timber.i(t, this)

fun String.logD(t: Throwable? = null) = Timber.d(t, this)

fun String.logW(t: Throwable? = null) = Timber.w(t, this)

fun String.logE(t: Throwable? = null) = Timber.e(t, this)

@SuppressLint("Deprecation")
fun String.htmlSpan(): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(this)
    }
}
