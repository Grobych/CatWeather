package com.globa.catweather.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.globa.catweather.utils.location.FusedLocationUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnSuccessListener
import java.util.*

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val tag = "LOCATION"
    init {
        Log.d(tag,"$this init")
    }

    val location = MutableLiveData<String>()
    val geocoder = Geocoder(getApplication(), Locale.getDefault())
    lateinit var fusedLocationClient : FusedLocationProviderClient




    @SuppressLint("MissingPermission")
    fun updateLocation(){
        Log.d("LOCATION", "update location...")
        fusedLocationClient.lastLocation.addOnSuccessListener { l : Location ->
            Log.d(tag, "return location")
            val addresses = geocoder.getFromLocation(l.latitude, l.longitude, 1)

            val cityName = addresses[0].locality
            val region = addresses[0].getAddressLine(1)
            val countryName = addresses[0].getAddressLine(2)
            Log.d(tag, "City: $cityName  Region: $region  Country: $countryName")
            location.postValue(cityName)
            Log.i(tag, "Get new location: $location")}
    }

}