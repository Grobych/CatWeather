package com.globa.catweather.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.globa.catweather.R
import com.globa.catweather.models.WeatherCodes
import com.globa.catweather.models.WeatherDrawable
import kotlin.random.Random

object ImageUtil {
    fun getByCode(code: Int, context: Context) : Drawable?{
        val weatherStatus = WeatherCodes().getByCode(code)
        val arrayId = WeatherDrawable.map[weatherStatus]
        return if (arrayId != null){
            val ta = context.resources.obtainTypedArray(arrayId)
            val id = ta.getResourceId(Random.nextInt(ta.length()),0)
            ta.recycle()
            ContextCompat.getDrawable(context, id)
        }else{
            ContextCompat.getDrawable(context, R.drawable.cat_weather_test)
        }
    }
}