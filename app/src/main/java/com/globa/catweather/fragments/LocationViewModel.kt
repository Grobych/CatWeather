package com.globa.catweather.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.globa.catweather.utils.location.FusedLocationUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import java.util.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlin.concurrent.thread


class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val tag = "LOCATION"
    val location = MutableLiveData<String>()
    val geocoder = Geocoder(getApplication(), Locale.getDefault())
    lateinit var fusedLocationClient : FusedLocationProviderClient

    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    init {
        Log.d(tag,"$this init")

    }

    @SuppressLint("MissingPermission")
    fun locationRequestInit(){
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        locationRequest.setInterval(60 * 1000) // TODO: set 1 hour(?)

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


    @SuppressLint("MissingPermission")
    fun updateLocation(){
        Log.d(tag, "update location...")
        fusedLocationClient.lastLocation.addOnSuccessListener { l : Location ->
            Log.d(tag, "return location")
            if (l == null) {
                Log.e(tag, "l : Location is null")
                return@addOnSuccessListener
            } else{
                val addresses = geocoder.getFromLocation(l.latitude, l.longitude, 1)
                val cityName = addresses[0].locality
                val region = addresses[0].getAddressLine(1)
                val countryName = addresses[0].getAddressLine(2)
                Log.d(tag, "City: $cityName  Region: $region  Country: $countryName")
                location.postValue(cityName)
                Log.i(tag, "Get new location: $location")}
            }

    }


}