package com.acoders.marvelfanbook.core.platform

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import com.acoders.marvelfanbook.core.extensions.isSdkVersion
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkConnectivityManager  @Inject constructor(@ApplicationContext context: Context) {

    private val connectivityManager: ConnectivityManager

    init {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    val hasConnection: Flow<Boolean> = callbackFlow {

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }

            override fun onUnavailable() {
                super.onUnavailable()
                trySend(false)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                trySend(false)
            }
        }

        val networkRequest = getNetworkRequest()
        trySend(connectivityManager.isConnected())
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
    }
}

private fun getNetworkRequest(): NetworkRequest = NetworkRequest.Builder()
    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    .apply {
        if (isSdkVersion(Build.VERSION_CODES.M)) addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }.build()

@Suppress("DEPRECATION")
private fun ConnectivityManager.isConnected(): Boolean =
    if (isSdkVersion(Build.VERSION_CODES.M))
        this.getNetworkCapabilities(activeNetwork)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
    else
        activeNetworkInfo?.isConnected ?: false
