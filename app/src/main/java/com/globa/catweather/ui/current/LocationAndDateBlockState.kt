package com.globa.catweather.ui.current

import com.globa.catweather.ui.ScreenStateType

data class LocationAndDateBlockState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val location: String = "",
    val dayOfWeek: String = "",
    val dayOfMonth: Int = 1
)
