package com.globa.catweather.utils

import androidx.fragment.app.Fragment
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.DetailWeatherFragment
import com.globa.catweather.fragments.ForecastWeatherFragment
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.interfaces.UpdateInterface

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

    fun to(direction: ClickInterface.Direction) : Fragment{
        return when (direction){
            ClickInterface.Direction.LEFT -> toLeft()
            ClickInterface.Direction.RIGHT -> toRight()
        }
    }
    fun getCurrent() = list[current]

    fun setInterface(clickInterface : ClickInterface){
        list.forEach { f ->
            if (f is CurrentWeatherFragment) f.setInterface(clickInterface)
            if (f is DetailWeatherFragment) f.setInterface(clickInterface)
            if (f is ForecastWeatherFragment) f.setInterface(clickInterface)
        }
    }

    fun update(){
        val current = list[current]
        if (current is UpdateInterface) current.update()
    }
}