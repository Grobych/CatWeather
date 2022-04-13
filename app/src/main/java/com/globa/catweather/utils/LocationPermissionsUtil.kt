package com.globa.catweather.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Build
import com.globa.catweather.R.integer.location_permission_code
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

object LocationPermissionsUtil {
    fun hasLocationPermissions(context: Context) =
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION,)
        }

    @AfterPermissionGranted(location_permission_code)
    fun requestPermissions(context: Context, activity: Activity) {
        if(checkSimplePermission(context)) {
            return
        }
        EasyPermissions.requestPermissions(
            activity,
            "You need to accept location permissions to use this app.",
            context.resources.getInteger(location_permission_code),
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }

    private fun checkSimplePermission(context: Context) : Boolean{
        return (EasyPermissions.hasPermissions(context,Manifest.permission.ACCESS_COARSE_LOCATION))
    }
}