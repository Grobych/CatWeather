package com.globa.catweather.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.globa.catweather.models.DetailDayWeather
import com.globa.catweather.models.WeatherRepository

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