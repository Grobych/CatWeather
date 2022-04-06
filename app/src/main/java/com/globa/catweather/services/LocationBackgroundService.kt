package com.globa.catweather.services

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.globa.catweather.models.Weather
import com.globa.catweather.models.WeatherRepository
import com.globa.catweather.notifications.NotificationUtil
import com.globa.catweather.utils.LocationPermissionsUtil
import com.globa.catweather.utils.LocationUtil
import com.globa.catweather.viewmodels.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationResult
import java.util.*
import java.util.concurrent.TimeUnit


class LocationBackgroundService : LifecycleService() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var viewModel : LocationViewModel

    companion object{
        val location = MutableLiveData<Location>()
    }
    fun postLocation(l : Location){
        location.postValue(l)
    }

    @SuppressLint("VisibleForTests")
    override fun onCreate() {
        Log.d("LOCATION SERVICE", "created")
        fusedLocationProviderClient = FusedLocationProviderClient(this)
        requestLocationsUpdate(true)
        viewModel = LocationViewModel.getInstance(application)
        location.observe(this,{updated ->
            viewModel.location.postValue(LocationUtil.getCity(updated, Geocoder(this, Locale.getDefault())))
        })
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("LOCATION SERVICE", "start")
        startForegroundService(applicationContext)
        return super.onStartCommand(intent, flags, startId)
    }
    override fun onDestroy() {
        requestLocationsUpdate(false)
        super.onDestroy()
    }

    private fun startForegroundService(context: Context) {
        Log.d("LOCATION SERVICE", "start foreground")
        startForeground(NotificationUtil.currentWeatherNotificationId, NotificationUtil.getLocationServiceNotification(context))
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationsUpdate(isTracking: Boolean) {
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
    @SuppressLint("MissingPermission")
    fun requestLocation(){
        if (LocationPermissionsUtil.hasLocationPermissions(this)){
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { result -> postLocation(result) }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            result.locations.let { locations ->
                for(location in locations) {
                    Log.d("LOCATION SERVICE","NEW LOCATION: ${location.latitude}, ${location.longitude}")
                    postLocation(location)
                    WeatherRepository.updateCurrent(
                        applicationContext,
                        LocationUtil.getCity(location,Geocoder(applicationContext, Locale.getDefault())),
                        MutableLiveData(Weather(0.0,0.0,0.0,"","",0))
                    )
                }
            }
        }
    }
}