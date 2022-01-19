package com.globa.catweather.viewmodels

import androidx.lifecycle.ViewModel
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.DetailWeatherFragment
import com.globa.catweather.fragments.ForecastWeatherFragment
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.utils.FragmentSelector

class MainActivityViewModel() : ViewModel() {
    lateinit var fragmentSelector: FragmentSelector

    fun init(clickInterface: ClickInterface){
        val currentWeatherFragment = CurrentWeatherFragment()
        val detailWeatherFragment = DetailWeatherFragment()
        val forecastWeatherFragment = ForecastWeatherFragment()
        fragmentSelector = FragmentSelector(listOf(currentWeatherFragment,detailWeatherFragment,forecastWeatherFragment))
        setInterface(clickInterface)
    }

    fun setInterface(clickInterface: ClickInterface){
        fragmentSelector.setInterface(clickInterface)
    }
}