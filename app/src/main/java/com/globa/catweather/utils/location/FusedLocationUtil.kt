package com.globa.catweather.utils.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.schedule

class FusedLocationUtil : LocationUtil() {

    private lateinit var fusedLocationClient : FusedLocationProviderClient


    override fun init(activity : Activity){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
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

    @Override
    override fun checkLocation(context: Context, activity: Activity, listener: OnSuccessListener<Location>){
        if (checkPermission(context,activity))
            fusedLocationClient.lastLocation.addOnSuccessListener(listener)
    }

}