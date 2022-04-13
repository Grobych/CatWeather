package com.globa.catweather.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.globa.catweather.models.ForecastWeather
import com.globa.catweather.models.WeatherRepository
import kotlin.collections.ArrayList

class ForecastWeatherViewModel : ViewModel() {
    var forecastList = MutableLiveData<ArrayList<ForecastWeather>>()
    private var list = arrayListOf<ForecastWeather>()

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