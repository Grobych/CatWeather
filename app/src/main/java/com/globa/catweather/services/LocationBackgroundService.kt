package com.globa.catweather.services

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.globa.catweather.notifications.NotificationUtil
import com.globa.catweather.utils.LocationPermissionsUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import java.util.concurrent.TimeUnit


class LocationBackgroundService : Service() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var isFirstRun = true

    companion object{
        val location = MutableLiveData<Location>()
    }

    @SuppressLint("VisibleForTests")
    override fun onCreate() {
        Log.d("LOCATION SERVICE", "created")
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        updateLocation(true)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LOCATION SERVICE", "start")
        startForegroundService(applicationContext)
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        updateLocation(false)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun startForegroundService(context: Context) {
        Log.d("LOCATION SERVICE", "start foreground")

        startForeground(NotificationUtil.locationServiceNotificationId, NotificationUtil.getLocationServiceNotification(context))
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation(isTracking: Boolean) {
        if(isTracking) {
            if(LocationPermissionsUtil.hasLocationPermissions(this)) {
                val request = LocationRequest().apply {
                    interval = TimeUnit.MINUTES.toMillis(2)
                    fastestInterval = TimeUnit.MINUTES.toMillis(1)
                    priority = PRIORITY_HIGH_ACCURACY
                }
                fusedLocationProviderClient.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } else {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.let { locations ->
                for(location in locations) {
                    Log.d("LOCATION SERVICE","NEW LOCATION: ${location.latitude}, ${location.longitude}")
                }
            }
        }
    }
}