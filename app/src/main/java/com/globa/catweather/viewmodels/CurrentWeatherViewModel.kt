package com.globa.catweather.viewmodels

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.globa.catweather.models.Weather
import com.globa.catweather.models.WeatherRepository
import com.globa.catweather.models.WeatherStatus

class CurrentWeatherViewModel : ViewModel() {
    private lateinit var _currentWeather : Weather
    val currentWeather = MutableLiveData<Weather>()
    var currentImageDrawable : Drawable? = null
    var currentType : WeatherStatus? = null

    fun updateWeather(context: Context, city : String){
        if (WeatherRepository.isCurrentAllow(city)) {
            Log.d("REPOSITORY", "is allow")
            _currentWeather = WeatherRepository.getCurrentWeather()
            update()
        } else{
            Log.d("REPOSITORY", "don't allow")
            WeatherRepository.updateCurrent(context,city,currentWeather)
        }
    }

    private fun update(){
        currentWeather.postValue(_currentWeather)
    }
}