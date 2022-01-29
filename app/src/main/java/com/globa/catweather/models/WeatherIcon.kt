package com.globa.catweather.models

import com.globa.catweather.R

object WeatherIcon {
    private val map = mapOf(
        WeatherStatus.Sunny to R.drawable.ic_sunny,
        WeatherStatus.Cloudy to R.drawable.ic_cloudy_2,
        WeatherStatus.Overcast to R.drawable.ic_cloudy,
        WeatherStatus.Fog to R.drawable.ic_fog,
        WeatherStatus.Drizzle to R.drawable.ic_drizzle_,
        WeatherStatus.LightRain to R.drawable.ic_rain_1,
        WeatherStatus.Rain to R.drawable.ic_rain_2,
        WeatherStatus.HeavyRain to R.drawable.ic_rain_2,
        WeatherStatus.Snow to R.drawable.ic_snow,
        WeatherStatus.LightSnow to R.drawable.ic_snow,
        WeatherStatus.HeavySnow to R.drawable.ic_blizzard,
        WeatherStatus.Blizzard to R.drawable.ic_blizzard,
        WeatherStatus.Thunder to R.drawable.ic_thunder_2,
        WeatherStatus.Sleet to R.drawable.ic_sleet
    )

    fun getByWeatherStatus(status: WeatherStatus) = map[status]
}