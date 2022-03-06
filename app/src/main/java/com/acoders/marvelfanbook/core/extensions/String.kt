package com.acoders.marvelfanbook.core.extensions

import java.math.BigInteger
import java.security.MessageDigest

private const val MD5_RADIX = 16

fun String.md5(length: Int = 32): String =
    BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
        .toString(MD5_RADIX).padStart(length, '0')
