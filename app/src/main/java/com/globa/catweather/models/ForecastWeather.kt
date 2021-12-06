package com.globa.catweather.models

import java.util.*

data class ForecastWeather (val date: Date, val minT : Double, val maxT : Double, val maxWindSpeed : Double, val condition : String, val code : Int)