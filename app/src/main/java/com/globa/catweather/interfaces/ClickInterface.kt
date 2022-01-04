package com.globa.catweather.interfaces

interface ClickInterface {
    enum class To {LEFT, RIGHT}
    fun clicked(to : To)
}