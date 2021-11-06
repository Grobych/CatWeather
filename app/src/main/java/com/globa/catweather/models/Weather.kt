package com.globa.catweather.models

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class Weather (val temp : Double, val feelsLike : Double, val windSpeed : Double, val windDirection : String, val condition : String)