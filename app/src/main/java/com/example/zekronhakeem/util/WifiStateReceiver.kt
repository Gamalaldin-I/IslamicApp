package com.example.zekronhakeem.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log

class WifiStateReceiver(private val onWifiConnected: () -> Unit, private val onWifiDisconnected: () -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val connectivityManager = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.d("WifiStateReceiver", "Wi-Fi Connected")
                    onWifiConnected()
                } else {
                    Log.d("WifiStateReceiver", "Wi-Fi Disconnected")
                    onWifiDisconnected()
                }
            } else {
                val activeNetwork = connectivityManager.activeNetworkInfo
                if (activeNetwork != null && activeNetwork.isConnected && activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                    Log.d("WifiStateReceiver", "Wi-Fi Connected")
                    onWifiConnected()
                } else {
                    Log.d("WifiStateReceiver", "Wi-Fi Disconnected")
                    onWifiDisconnected()
                }
            }
        }
    }
}
