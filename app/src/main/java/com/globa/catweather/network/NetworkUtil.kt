package com.globa.catweather.network

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.globa.catweather.R
import java.lang.Exception
import java.net.InetAddress


class NetworkUtil {
    fun isNetworkConnected(context : Context): Boolean {
        val cm = getSystemService(context, ConnectivityManager::class.java)
        return if (cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected) true
        else {
            Toast.makeText(
                context,
                context.getText(R.string.no_network_connection),
                Toast.LENGTH_LONG
            ).show()
            false
        }
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