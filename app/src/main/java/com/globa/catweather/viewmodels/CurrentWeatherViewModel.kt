package com.globa.catweather.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.globa.catweather.models.Weather
import org.json.JSONObject

class CurrentWeatherViewModel : ViewModel() {
    private lateinit var currentWeather : Weather
    val currentT = MutableLiveData<Double>()
    val windDirection = MutableLiveData<String>()
    val windSpeed = MutableLiveData<Double>()
    val condition = MutableLiveData<String>()
    val feelsLike = MutableLiveData<Double>()
    val code = MutableLiveData<Int>()

    fun updateWeather(context: Context, city : String){

        val url =
            "http://api.weatherapi.com/v1/current.json?key=ff946992f1ee4f3d80385853210111&q=$city&aqi=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i("JSON TEST", response)
                val json = JSONObject(JSONObject(response).getString("current"))
                Log.d("JSON","$json")
                currentWeather = Weather(
                    json.getDouble("temp_c"),
                    json.getDouble("feelslike_c"),
                    json.getDouble("wind_kph"),
                    json.getString("wind_dir"),
                    json.getJSONObject("condition").getString("text"),
                    json.getJSONObject("condition").getInt("code"))
                update()
            },
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }
    private fun update(){
        currentT.postValue(currentWeather.temp)
        feelsLike.postValue(currentWeather.feelsLike)
        windDirection.postValue(currentWeather.windDirection)
        windSpeed.postValue(currentWeather.windSpeed)
        condition.postValue(currentWeather.condition)
        code.postValue(currentWeather.code)
    }
}