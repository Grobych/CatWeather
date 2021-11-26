package com.globa.catweather.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.globa.catweather.R
import com.globa.catweather.models.ForecastWeather
import org.json.JSONArray
import org.json.JSONObject

class ForecastWeatherViewModel : ViewModel() {
    var forecastList = MutableLiveData<ArrayList<ForecastWeather>>()
    var list = arrayListOf<ForecastWeather>()

    fun updateForecast(context: Context, city : String){
        val dayNumber = context.resources.getInteger(R.integer.forecastDayNumber)
        val url =
            "http://api.weatherapi.com/v1/forecast.json?key=ff946992f1ee4f3d80385853210111&q=$city&days=$dayNumber&aqi=no&alerts=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i("JSON TEST", response)
                list.clear()
                val forecast = JSONObject(JSONObject(response).getString("forecast"))
                val forecastDay = forecast.getString("forecastday")
                val days = JSONArray(forecastDay)
                Log.d("DAYS", "$dayNumber")
                for (i in 0 until dayNumber){
                    val day = days.getJSONObject(i).getJSONObject("day")
                    val minT = day.getDouble("mintemp_c")
                    val maxT = day.getDouble("maxtemp_c")
                    val wind = day.getDouble("maxwind_kph")
                    val condition = day.getJSONObject("condition").getString("text")
                    val code = day.getJSONObject("condition").getInt("code")
                    list.add(ForecastWeather(minT,maxT,wind,condition,code))
                }
                forecastList.postValue(list)
            },
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
    }
}