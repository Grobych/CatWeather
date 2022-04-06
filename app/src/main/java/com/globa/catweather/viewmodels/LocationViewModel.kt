package com.globa.catweather.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.globa.catweather.services.LocationBackgroundService
import com.globa.catweather.utils.LocationUtil
import com.google.android.gms.location.FusedLocationProviderClient
import java.util.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.concurrent.TimeUnit

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val tag = "LOCATION"
    val location = MutableLiveData<String>()
    val geocoder = Geocoder(getApplication(), Locale.getDefault())
    lateinit var fusedLocationClient : FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    companion object{
        private lateinit var instance : LocationViewModel

        @MainThread
        fun getInstance(application: Application): LocationViewModel {
            instance = if(Companion::instance.isInitialized) instance else LocationViewModel(application)
            return instance
        }
    }

    @SuppressLint("MissingPermission")
    fun locationRequestInit(){
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = TimeUnit.MINUTES.toMillis(30)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val cityName = LocationUtil.getCity(locationResult,geocoder)
                Log.d(tag, "${location.value} :::: $cityName")
                if (!location.value.equals(cityName)){
                    Log.d(tag,"Posting location")
                    location.postValue(cityName)
                }
                Log.i(tag, "Get new location: $location")
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,
            Looper.myLooper()!!
        )
    }
}