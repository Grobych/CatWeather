package com.globa.catweather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat

open abstract class LocationUtil {
    protected val LOCATION_PERMISSION_CODE = 1000
    lateinit var currentLocation: Location

    abstract fun init(context : Context, activity : Activity)
    abstract fun checkLocation()
    abstract fun checkPermission(context : Context, activity : Activity) : Boolean
}

