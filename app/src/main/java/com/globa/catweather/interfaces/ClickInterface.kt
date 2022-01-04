package com.globa.catweather.interfaces

import androidx.fragment.app.Fragment

interface ClickInterface {
    enum class To {LEFT, RIGHT}
    fun clicked(to : To, from : Fragment)
}