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
import com.globa.catweather.models.WeatherRepository
import org.json.JSONArray
import org.json.JSONObject

class DetailWeatherViewModel : ViewModel() {
    val detailDayWeather = MutableLiveData<DetailDayWeather>()
    private lateinit var _detailDayWeather : DetailDayWeather

    fun updateWeather(context: Context, city : String){
        if (WeatherRepository.isDetailAllow(city)) {
            Log.d("REPOSITORY", "is allow")
            _detailDayWeather = WeatherRepository.getDetailWeather()
            update()
        } else{
            Log.d("REPOSITORY", "don't allow")
            WeatherRepository.updateDetail(context,city,detailDayWeather)
        }
    }

    private fun update(){
        detailDayWeather.postValue(_detailDayWeather)
    }
}