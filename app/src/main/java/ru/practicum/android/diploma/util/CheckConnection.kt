package ru.practicum.android.diploma.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class CheckConnection(val context: Context) {
    private var result = false
    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkConnectNewVersion(connectivityManager)
        } else {
            checkConnectOldVersion(connectivityManager)
        }
        return result
    }

    private fun checkConnectNewVersion(connectivityManager: ConnectivityManager) {
        val networkCapabilities = connectivityManager.activeNetwork
        val actNw =
            connectivityManager.getNetworkCapabilities(networkCapabilities)
        result = if (actNw == null || networkCapabilities == null) {
            false
        } else {
            when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
    }

    private fun checkConnectOldVersion(connectivityManager: ConnectivityManager) {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
}
