package com.globa.catweather.utils

import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationResult

object LocationUtil {

    fun getCity(locationResult : LocationResult, geocoder: Geocoder) : String?{
        for (l in locationResult.locations) {
            if (l != null) {
                return getCity(l,geocoder)
            }
        }
        return "undefined"
    }
    private fun getCity(l: Location, geocoder: Geocoder) : String?{
        val addresses = geocoder.getFromLocation(l.latitude, l.longitude, 1)
        val cityName = addresses?.get(0)?.locality
        val region = addresses?.get(0)?.getAddressLine(1)
        val countryName = addresses?.get(0)?.getAddressLine(2)
        Log.d("LOCATION UTIL", "City: $cityName  Region: $region  Country: $countryName")
        return cityName
    }
}