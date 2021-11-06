package com.globa.catweather.models

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import javax.security.auth.callback.Callback
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class RepoWeather {
    lateinit var weather: Weather

    fun updateWeather(onDataReadyCallback: OnDataReadyCallback){
        val url = "http://api.weatherapi.com/v1/current.json?key=ff946992f1ee4f3d80385853210111&q=Minsk&aqi=no"
        val queue = Volley.newRequestQueue(this as Context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i("JSON TEST", response)
                val json : JSONObject = response as JSONObject
                weather = Weather(
                    json.getDouble("temp_c"),
                    json.getDouble("feelslike_c"),
                    json.getDouble("wind_kph"),
                    json.getString("wind_dir"),
                    json.getJSONObject("condition").getString("text"))
            },
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }
}

interface OnDataReadyCallback {
    fun onDataReady(data : Weather)
}