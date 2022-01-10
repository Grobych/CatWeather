package com.globa.catweather.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.globa.catweather.R
import com.globa.catweather.models.ForecastWeather
import com.globa.catweather.models.WeatherRepository
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class ForecastWeatherViewModel : ViewModel() {
    var forecastList = MutableLiveData<ArrayList<ForecastWeather>>()
    var list = arrayListOf<ForecastWeather>()

    fun updateForecast(context: Context, city : String){
        if (WeatherRepository.isForecastAllow(city)) {
            Log.d("REPOSITORY", "is allow")
            list = WeatherRepository.getForecastWeather()
            update()
        } else{
            Log.d("REPOSITORY", "don't allow")
            WeatherRepository.updateForecast(context,city,forecastList)
        }
    }

    private fun update(){
        forecastList.postValue(list)
    }
}