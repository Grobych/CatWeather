package com.globa.catweather.ui.current

import com.globa.catweather.ui.ScreenStateType

data class MainScreenFooterBlockState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val map: Map<String, Int> = mapOf() //TODO: review this
)
