package com.globa.catweather.models

import com.globa.catweather.R
import com.globa.catweather.models.WeatherStatus.*


class WeatherDrawable {
    val map = mapOf(
        Sunny to R.array.image_sunny,
        Cloudy to R.array.image_cloudy,
        Overcast to R.array.image_overcast,
        Fog to R.array.image_fog,
        Drizzle to R.array.image_drizzle,
        LightRain to R.array.image_light_rain,
        Rain to R.array.image_rain,
        HeavyRain to R.array.image_heavy_rain,
        Snow to R.array.image_snow,
        LightSnow to R.array.image_light_snow,
        HeavySnow to R.array.image_heavy_snow,
        Blizzard to R.array.image_blizzard,
        Thunder to R.array.image_thunder,
        Sleet to R.array.image_sleet
    )
}