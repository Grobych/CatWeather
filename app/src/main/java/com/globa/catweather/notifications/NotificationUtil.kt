package com.globa.catweather.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.globa.catweather.R
import com.globa.catweather.activities.MainActivity
import com.globa.catweather.models.Weather

object NotificationUtil {

    private const val channelId = "CatWeatherNotificationChannel"
    const val currentWeatherNotificationId = 1
    private var initialized = false

    private lateinit var channel: NotificationChannel
    private lateinit var notificationManager: NotificationManager

    private fun init(context: Context){
        notificationManager = context.applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel = generateChannel(context)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }

        initialized = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateChannel(context: Context): NotificationChannel {
        return NotificationChannel(
            channelId,
            context.getString(R.string.notificationChannelName),
            NotificationManager.IMPORTANCE_LOW
        )
    }

    private fun getCurrentWeatherNotification(context: Context, weather : Weather, icon : Drawable) : Notification{
        if (!initialized) init(context)

        val builder =  NotificationCompat.Builder(context, channelId)

        val intent = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)

        val bigText = NotificationCompat.BigTextStyle()
        bigText.setBigContentTitle(context.getString(R.string.current_weather_temperature_template,weather.temp))
        bigText.setSummaryText(weather.condition)

        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(R.drawable.ic_cloud_test)
        builder.setContentTitle(context.getString(R.string.current_weather_temperature_template,weather.temp))
        builder.setContentText("Feels like: " + context.getString(R.string.current_weather_feelslike_template,weather.feelsLike))
        builder.priority = Notification.PRIORITY_DEFAULT
        builder.setStyle(bigText)
        builder.setContentIntent(pendingIntent)
        builder.setLargeIcon(icon.toBitmap())
        builder.setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId)
        }
        return builder.build()
    }
    fun postCurrentWeatherNotification(context: Context, weather: Weather, icon: Drawable){
        if (!initialized) init(context)
        notificationManager.notify(currentWeatherNotificationId,
            getCurrentWeatherNotification(context, weather, icon)
        )
    }

    fun getForegroundServiceNotification(context: Context) : Notification{
        if (!initialized) init(context)

        val builder =  NotificationCompat.Builder(context, channelId)

        val intent = Intent(context.applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, FLAG_IMMUTABLE)

        builder.setContentIntent(pendingIntent)
        builder.setSmallIcon(R.drawable.ic_cloud_test)
        builder.setContentTitle("Getting weather in foreground...")
        builder.setContentText("Tap to open app")
        builder.priority = Notification.PRIORITY_DEFAULT
        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId)
        }
        return builder.build()
    }

}