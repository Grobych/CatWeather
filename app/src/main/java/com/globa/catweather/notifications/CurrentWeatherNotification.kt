package com.globa.catweather.notifications

import android.app.PendingIntent
import android.content.Intent
import android.app.Notification
import android.app.NotificationChannel

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.globa.catweather.R

import com.globa.catweather.activities.MainActivity
import com.globa.catweather.models.Weather


class CurrentWeatherNotification {

    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "CatWeatherChannelId"
    }

    fun generateCurrentWeatherNotification(context: Context, weather : Weather){
        val mBuilder = NotificationCompat.Builder(context.applicationContext, "notify_001")
        val ii = Intent(context.applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, ii, 0)

        val bigText = NotificationCompat.BigTextStyle()
//        bigText.bigText("verseurl")
        bigText.setBigContentTitle(context.getString(R.string.current_weather_temperature_template,weather.temp))
        bigText.setSummaryText(weather.condition)

//        mBuilder.setContentIntent(pendingIntent)
        mBuilder.setSmallIcon(R.drawable.ic_cloud_test)
        mBuilder.setContentTitle("Title")
        mBuilder.setContentText("Feelslike: " + context.getString(R.string.current_weather_feelslike_template,weather.feelsLike))
        mBuilder.priority = Notification.PRIORITY_DEFAULT
        mBuilder.setStyle(bigText)

        val mNotificationManager: NotificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = CHANNEL_ID
            val channel = NotificationChannel(
                channelId,
                "CatWeather notification channel",
                NotificationManager.IMPORTANCE_LOW
            )
            mNotificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }

        mNotificationManager.notify(0, mBuilder.build())
    }
}