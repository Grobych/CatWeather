package com.globa.catweather.utils.location

import android.app.Activity
import android.content.Context
import android.location.Location
import com.google.android.gms.tasks.OnSuccessListener

open abstract class LocationUtil {
    protected val LOCATION_PERMISSION_CODE = 1000
    lateinit var currentLocation: Location

    abstract fun init(activity: Activity)
    abstract fun checkLocation(context: Context, activity: Activity, listener: OnSuccessListener<Location>)
    abstract fun checkPermission(context : Context, activity : Activity) : Boolean
}

