package com.globa.catweather.services

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.globa.catweather.models.Weather
import com.globa.catweather.models.WeatherRepository
import com.globa.catweather.notifications.NotificationUtil
import com.globa.catweather.utils.ImageUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateWorker(appContext: Context, workerParams: WorkerParameters):
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("WORKER", "do work")
        val weather = MutableLiveData<Weather>()
        GlobalScope.launch (Dispatchers.Main) {
            weather.observeForever { updated ->
                Log.d("OBSERVER WORKER", "$updated")
                NotificationUtil.postCurrentWeatherNotification(
                    applicationContext,
                    updated,
                    ImageUtil.getByCode(updated.code, applicationContext)!!
                )
            }
        }

        sendRequest(weather)
        return Result.success()
    }
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            NotificationUtil.currentWeatherNotificationId, createNotification()
        )
    }

    private fun createNotification() : Notification {
        return NotificationUtil.getForegroundServiceNotification(applicationContext)
    }

    private fun sendRequest(data: MutableLiveData<Weather>){

        WeatherRepository.updateForeground(applicationContext,data)
    }
}