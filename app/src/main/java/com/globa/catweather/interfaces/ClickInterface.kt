package com.globa.catweather.interfaces

interface ClickInterface {
    enum class Direction {LEFT, RIGHT}
    fun clicked(direction : Direction)
}