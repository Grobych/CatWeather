package com.globa.catweather.utils

import android.view.GestureDetector
import android.view.MotionEvent
import com.globa.catweather.interfaces.ClickInterface

class SwipeGestureDetector(private val clickInterface: ClickInterface) : GestureDetector.SimpleOnGestureListener() {

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if ((distanceY < 20) && (distanceX > 50)) clickInterface.clicked(ClickInterface.To.RIGHT)
        if ((distanceY < 20) && (distanceX < -50)) clickInterface.clicked(ClickInterface.To.LEFT)
        return true
    }
}