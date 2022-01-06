package com.globa.catweather.models

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object WeatherRepository {
    private lateinit var currentWeather : Weather
    private var forecastWeather : ForecastWeather? = null
    private var detailDayWeather: DetailDayWeather? = null

    private var latestCity = ""
    private var currentWeatherUptime = 0L
    private var forecastWeatherUptime = 0L
    private var detailWeatherUptime = 0L

    fun isCurrentAllow(city : String) : Boolean {
        return !((currentWeatherUptime == 0L)
                || (latestCity != city)
                || (System.currentTimeMillis() - currentWeatherUptime > TimeUnit.MINUTES.toMillis(60)))
    }

    fun getCurrentWeather() = currentWeather

    fun setCurrentWeather(current : Weather, city: String){
        currentWeather = current
        latestCity = city
        currentWeatherUptime = System.currentTimeMillis()
    }



    fun updateCurrent(context: Context, city : String, updating : MutableLiveData<Weather>){
        latestCity = city
        currentWeatherUptime = System.currentTimeMillis()

        val url = "http://api.weatherapi.com/v1/current.json?key=ff946992f1ee4f3d80385853210111&q=$city&aqi=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            listener(updating),
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }

    private fun listener(updating: MutableLiveData<Weather>) = Response.Listener<String> { response ->
        Log.i("JSON TEST", response)
        val json = JSONObject(JSONObject(response).getString("current"))
        currentWeather = Weather(
            json.getDouble("temp_c"),
            json.getDouble("feelslike_c"),
            json.getDouble("wind_kph"),
            json.getString("wind_dir"),
            json.getJSONObject("condition").getString("text"),
            json.getJSONObject("condition").getInt("code"))
        updating.postValue(currentWeather)
    }
}