package com.globa.catweather.ui.current

import com.globa.catweather.ui.ScreenStateType

data class CurrentWeatherScreenState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val weatherState: String,
    val temperature: Float,
    val feelsLikeTemperature: Float,
    val windSpeed: Float, //?
    val humidity: Int,
    val image: Int
)