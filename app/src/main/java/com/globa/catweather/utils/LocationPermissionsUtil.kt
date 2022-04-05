package com.globa.catweather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import com.globa.catweather.R
import pub.devrel.easypermissions.EasyPermissions

object LocationPermissionsUtil {
    fun hasLocationPermissions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }


    fun requestPermissions(context: Context, activity: Activity) {
        if(hasLocationPermissions(context)) {
            return
        }
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                activity,
                "You need to accept location permissions to use this app.",
                context.resources.getInteger(R.integer.location_permission_code),
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                activity,
                "You need to accept location permissions to use this app.",
                context.resources.getInteger(R.integer.location_permission_code),
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }
}