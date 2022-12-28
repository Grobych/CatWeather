package com.globa.catweather.ui.current

import com.globa.catweather.ui.ScreenStateType

data class MainInfoBlockState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val weatherState: String = "",
    val temperature: Int = 0,
    val feelsLikeTemperature: Int = 0,
    val windSpeed: Int = 0,
    val humidity: Int = 0,
)
