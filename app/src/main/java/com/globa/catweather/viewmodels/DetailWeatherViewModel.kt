package com.globa.catweather.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.globa.catweather.models.Astro
import com.globa.catweather.models.DetailDayWeather
import com.globa.catweather.models.Weather
import org.json.JSONArray
import org.json.JSONObject

class DetailWeatherViewModel : ViewModel() {
    val detailDayWeather = MutableLiveData<DetailDayWeather>()
    private lateinit var _detailDayWeather : DetailDayWeather

    fun updateWeather(context: Context, city : String){

        val url =
            "http://api.weatherapi.com/v1/forecast.json?key=ff946992f1ee4f3d80385853210111&q=$city&days=1&aqi=no&alerts=no"
        Log.d("WEATHER URL", url)
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
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
                _detailDayWeather = DetailDayWeather(current, astro, precipitation, humidity, visibility, pressure)
                update()
            },
            {
                val text = "That didn't work!"
                Log.e("Weather update", text)
            })
        queue.add(stringRequest)
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


    private fun update(){
        detailDayWeather.postValue(_detailDayWeather)
    }
}