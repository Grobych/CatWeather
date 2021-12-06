package com.globa.catweather.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import java.lang.Exception
import java.net.InetAddress


class NetworkUtil {
    fun isNetworkConnected(context : Context): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }
    fun isInternetAvailable(): Boolean {
        return try {
            val ip: InetAddress = InetAddress.getByName("google.com")
            !ip.equals("")
        } catch (e: Exception) {
            false
        }
    }
}