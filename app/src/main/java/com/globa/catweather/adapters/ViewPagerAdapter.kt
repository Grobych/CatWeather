package com.globa.catweather.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.DetailWeatherFragment
import com.globa.catweather.fragments.ForecastWeatherFragment

class ViewPagerAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentWeatherFragment()
            1 -> DetailWeatherFragment()
            2 -> ForecastWeatherFragment()
            else -> CurrentWeatherFragment()
        }
    }
}