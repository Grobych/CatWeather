package com.globa.catweather.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
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

    @SuppressLint("MissingPermission")
    fun locationRequestInit(){
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = TimeUnit.MINUTES.toMillis(30)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult == null) {
                    return
                }
                for (l in locationResult.locations) {
                    if (l != null) {
                        val addresses = geocoder.getFromLocation(l.latitude, l.longitude, 1)
                        val cityName = addresses[0].locality
                        val region = addresses[0].getAddressLine(1)
                        val countryName = addresses[0].getAddressLine(2)
                        Log.d(tag, "City: $cityName  Region: $region  Country: $countryName")
                        Log.d(tag, "${location.value} :::: $cityName")
                        if (!location.value.equals(cityName)){
                            Log.d(tag,"Posting location")
                            location.postValue(cityName)
                        }
                        Log.i(tag, "Get new location: $location")
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback,Looper.myLooper())
    }
}