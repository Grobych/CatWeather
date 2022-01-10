package com.globa.catweather.models

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.globa.catweather.R
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

object WeatherRepository {
    private lateinit var currentWeather : Weather
    private var forecastWeather = arrayListOf<ForecastWeather>()
    private var detailDayWeather: DetailDayWeather? = null

    private var latestCity = ""
    private var currentWeatherUptime = 0L
    private var forecastWeatherUptime = 0L
    private var detailWeatherUptime = 0L

    fun isCurrentAllow(city : String) : Boolean {
        return isAllow(city, currentWeatherUptime)
    }
    fun isForecastAllow(city : String) : Boolean{
        return isAllow(city, forecastWeatherUptime)
    }
    fun isDetailAllow(city : String) : Boolean{
       return isAllow(city, detailWeatherUptime)
    }
    private fun isAllow(city: String, uptime : Long) :Boolean{
        return !((uptime == 0L)
                || (latestCity != city)
                || (System.currentTimeMillis() - uptime > TimeUnit.MINUTES.toMillis(60)))
    }

    fun getCurrentWeather() = currentWeather
    fun getForecastWeather() = forecastWeather
    fun getDetailWeather() = detailDayWeather

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
            listenerCurrent(updating),
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }

    fun updateForecast(context: Context, city :String, updating: MutableLiveData<ArrayList<ForecastWeather>>){
        latestCity = city
        forecastWeatherUptime = System.currentTimeMillis()

        val dayNumber = context.resources.getInteger(R.integer.forecast_day_number)
        val url =
            "http://api.weatherapi.com/v1/forecast.json?key=ff946992f1ee4f3d80385853210111&q=$city&days=$dayNumber&aqi=no&alerts=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            listenerForecast(updating, dayNumber),
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }

    private fun listenerCurrent(updating: MutableLiveData<Weather>) = Response.Listener<String> { response ->
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

    private fun listenerForecast(updating: MutableLiveData<ArrayList<ForecastWeather>>, dayNumber : Int) = Response.Listener<String> { response ->
        Log.i("JSON TEST", response)
        forecastWeather.clear()
        val forecast = JSONObject(JSONObject(response).getString("forecast"))
        val forecastDay = forecast.getString("forecastday")
        val days = JSONArray(forecastDay)
        for (i in 0 until dayNumber){
            val dateEpoch = days.getJSONObject(i).getString("date_epoch").toLong() * 1000
            val date = Date(dateEpoch)
            val day = days.getJSONObject(i).getJSONObject("day")
            val minT = day.getDouble("mintemp_c")
            val maxT = day.getDouble("maxtemp_c")
            val wind = day.getDouble("maxwind_kph")
            val condition = day.getJSONObject("condition").getString("text")
            val code = day.getJSONObject("condition").getInt("code")
            forecastWeather.add(ForecastWeather(date,minT,maxT,wind,condition,code))
        }
        Log.d("REPOSITORY", "$forecastWeather")
        updating.postValue(forecastWeather)
    }
}