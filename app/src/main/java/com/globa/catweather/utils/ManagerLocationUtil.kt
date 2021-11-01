package com.globa.catweather.utils

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager

class ManagerLocationUtil : LocationUtil(), LocationListener {
    private lateinit var locationManager: LocationManager

    override fun init(context: Context, activity: Activity) {
        TODO("Not yet implemented")
    }

    override fun checkLocation() {
        TODO("Not yet implemented")
    }

    override fun checkPermission(context: Context, activity: Activity): Boolean {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {
        TODO("Not yet implemented")
    }
}