package com.globa.catweather.ui.current

import com.globa.catweather.ui.ScreenStateType

data class CurrentWeatherScreenState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val weatherState: String,
    val temperature: Int,
    val feelsLikeTemperature: Int,
    val windSpeed: Int,
    val humidity: Int,
    val image: Int
)