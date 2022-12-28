package com.globa.catweather.ui.current

import com.globa.catweather.R
import com.globa.catweather.ui.ScreenStateType

data class MainImageBlockState(
    val state: ScreenStateType = ScreenStateType.LOADING,
    val image: Int = R.drawable.winter_cat_preview
)