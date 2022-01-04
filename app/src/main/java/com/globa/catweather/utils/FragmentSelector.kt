package com.globa.catweather.utils

import androidx.fragment.app.Fragment
import com.globa.catweather.interfaces.ClickInterface

class FragmentSelector(private val list: List<Fragment>) {
    var current = 0

    fun toRight() : Fragment{
        if (current == list.lastIndex) current = 0
        else current++
        return list[current]
    }

    fun toLeft() : Fragment{
        if (current == 0) current = list.lastIndex
        else current--
        return list[current]
    }

    fun to(to: ClickInterface.To) : Fragment{
        return when (to){
            ClickInterface.To.LEFT -> toLeft()
            ClickInterface.To.RIGHT -> toRight()
        }
    }
    fun getCurrent() = list[current]
}