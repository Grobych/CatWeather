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
    private lateinit var detailDayWeather: DetailDayWeather

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

    fun updateForeground(context: Context, data: MutableLiveData<Weather>){
        if (latestCity != "") updateCurrent(context, latestCity, data)
    }


    fun updateCurrent(context: Context, city : String, updating : MutableLiveData<Weather>){
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

    fun updateDetail(context: Context, city : String, updating : MutableLiveData<DetailDayWeather>){
        val url =
            "http://api.weatherapi.com/v1/forecast.json?key=ff946992f1ee4f3d80385853210111&q=$city&days=1&aqi=no&alerts=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            listenerDetail(updating),
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

        latestCity = JSONObject(response).getJSONObject("location").getString("region")
        currentWeatherUptime = System.currentTimeMillis()
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

        latestCity = JSONObject(response).getJSONObject("location").getString("region")
        forecastWeatherUptime = System.currentTimeMillis()
    }

    private fun listenerDetail(updating: MutableLiveData<DetailDayWeather>) = Response.Listener<String> { response ->
        Log.i("JSON TEST", response)
        val json = JSONObject(response)
        Log.d("JSON","$json")
        val current = getCurrent(json)
        val astro = getAstro(json)
        val forecastDay = json.getJSONObject("forecast").getString("forecastday")
        val days = JSONArray(forecastDay)
        val day = days.getJSONObject(0).getJSONObject("day")
        val precipitation = day.getDouble("totalprecip_mm")
        val humidity = day.getDouble("avghumidity")
        val visibility = day.getDouble("avgvis_km")
        val pressure = json.getJSONObject("current").getDouble("pressure_mb")
        detailDayWeather = DetailDayWeather(current, astro, precipitation, humidity, visibility, pressure)
        updating.postValue(detailDayWeather)

        latestCity = json.getJSONObject("location").getString("region")
        detailWeatherUptime = System.currentTimeMillis()
    }

    private fun getCurrent(json: JSONObject): Weather {
        val jsonCurrent = json.getJSONObject("current")
        return Weather(
            jsonCurrent.getDouble("temp_c"),
            jsonCurrent.getDouble("feelslike_c"),
            jsonCurrent.getDouble("wind_kph"),
            jsonCurrent.getString("wind_dir"),
            jsonCurrent.getJSONObject("condition").getString("text"),
            jsonCurrent.getJSONObject("condition").getInt("code")
        )
    }
    private fun getAstro(json: JSONObject) : Astro{
        val forecast = JSONObject(json.getString("forecast"))
        val forecastDay = forecast.getString("forecastday")
        val days = JSONArray(forecastDay)
        val jsonAstro = days.getJSONObject(0).getJSONObject("astro")
        return Astro(
            jsonAstro.getString("sunrise"),
            jsonAstro.getString("sunset"),
            jsonAstro.getString("moonrise"),
            jsonAstro.getString("moonset"),
            jsonAstro.getString("moon_phase"),
            jsonAstro.getString("moon_illumination"),
        )
    }

}