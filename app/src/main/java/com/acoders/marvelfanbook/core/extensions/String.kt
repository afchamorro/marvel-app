package com.acoders.marvelfanbook.core.extensions

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
