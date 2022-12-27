package com.globa.catweather.ui.current

import android.location.Location
import com.globa.catweather.ui.ScreenStateType
import java.util.Date

data class DateAndLocationScreenState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val date: Date,
    val location: Location
)
