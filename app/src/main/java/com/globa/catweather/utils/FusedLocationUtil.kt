package com.globa.catweather.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class FusedLocationUtil : LocationUtil() {

    private lateinit var fusedLocationClient : FusedLocationProviderClient


    override fun init(context : Context, activity : Activity){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        if (checkPermission(context, activity)) {
            Timer().schedule(TimeUnit.MINUTES.toMillis(0), TimeUnit.MINUTES.toMillis(1)){ checkLocation()}
        }
    }

    @Override
    override fun checkPermission(context : Context, activity : Activity) : Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE
            );
            false
        } else true
    }

    @SuppressLint("MissingPermission")
    @Override
    override fun checkLocation(){
        fusedLocationClient.lastLocation.addOnSuccessListener {
                location ->
                this.currentLocation = location
                Log.i("LOCATION", "Get new location: $location")
        }
    }

}