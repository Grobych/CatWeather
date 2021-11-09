package com.globa.catweather.models

data class Weather (val temp : Double, val feelsLike : Double, val windSpeed : Double, val windDirection : String, val condition : String, val code : Int)