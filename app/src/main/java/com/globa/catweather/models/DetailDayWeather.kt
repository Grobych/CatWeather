package com.globa.catweather.models

data class DetailDayWeather(val current : Weather,
                            val astro: Astro,
                            val precipitation : Double,
                            val humidity : Double,
                            val visibility : Double)