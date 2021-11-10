package com.globa.catweather.models

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.globa.catweather.R
import java.util.*
import kotlin.coroutines.coroutineContext

enum class WeatherDrawable(val code : Int, val drawable: Int) {
    Sunny(1000,R.drawable.sunny),
    PartlyCloudy(1003, R.drawable.partly_cloudy),
    Cloudy(1006, R.drawable.cloudy),
    Overcast(1009,R.drawable.overcast),
    Mist(1030,R.drawable.mist),
    Blizzard(1117,R.drawable.blizzard),
    Fog(1135,R.drawable.fog),
    LightRain(1180,R.drawable.light_rain),
    ModerateRain(1186,R.drawable.moderate_rain),
    HeavyRain(1192,R.drawable.heavy_rain);
}

