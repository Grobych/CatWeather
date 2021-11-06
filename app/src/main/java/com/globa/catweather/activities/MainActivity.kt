package com.globa.catweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.globa.catweather.R
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.LocationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        if (savedInstanceState == null) {
//
//            // Adds our fragment
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<LocationFragment>(R.id.locationFragmentContainerView)
//                add<CurrentWeatherFragment>(R.id.currentWeatherFragmentContainerView)
//            }
//        }
    }
}