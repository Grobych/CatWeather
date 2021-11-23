package com.globa.catweather.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.globa.catweather.models.ForecastWeather
import org.json.JSONArray
import org.json.JSONObject

class ForecastWeatherViewModel : ViewModel() {
    var forecastList = MutableLiveData<ArrayList<ForecastWeather>>()
    var list = arrayListOf<ForecastWeather>()

    val days : Int = 3 // TODO: add forecast days to constants

    fun updateForecast(context: Context, city : String){
        val url =
            "http://api.weatherapi.com/v1/forecast.json?key=ff946992f1ee4f3d80385853210111&q=$city&days=$days&aqi=no&alerts=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i("JSON TEST", response)
                val forecast = JSONObject(JSONObject(response).getString("forecast"))
                val forecastDay = forecast.getString("forecastday")
                Log.d("FORECAST DAY", forecastDay)
                val days = JSONArray(forecastDay)
                Log.d("DAYS", "${days.length()}")
//                for (i in 1..days){
//                    val days = JSONArray(forecastDay)
//                    Log.d("WEATHER FORECAST", days.toString())
//                }
            },
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }
}