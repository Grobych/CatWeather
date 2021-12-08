package com.globa.catweather.models
import com.globa.catweather.models.WeatherStatus.*

class WeatherCodes{
    private val map = mapOf(
        Sunny to listOf(1000),
        Cloudy to listOf(1003,1006),
        Overcast to listOf(1009),
        Fog to listOf(1030,1135,1147),
        Drizzle to listOf(1150,1153,1168,1171),
        LightRain to listOf(1180,1183,1198,1072),
        Rain to listOf(1063,1186,1189,1201),
        HeavyRain to listOf(1192,1195,1189,1240,1243,1246),
        Snow to listOf(1066,1216,1219),
        LightSnow to listOf(1210,1213),
        HeavySnow to listOf(1222,1225,1255,1258),
        Blizzard to listOf(1117,1114),
        Thunder to listOf(1087,1273,1276,1279,1282),
        Sleet to listOf(1069,1204,1207,1249,1252,1237,1261,1264)
    )

    fun getByCode(code : Int) : WeatherStatus{
        return map.filterValues { it.contains(code) }.keys.first()
    }
}