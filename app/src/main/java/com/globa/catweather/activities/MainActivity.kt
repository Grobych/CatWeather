package com.globa.catweather.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.*
import com.globa.catweather.R
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.DetailWeatherFragment
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.view.OnSwipeTouchListener


class MainActivity : AppCompatActivity(), ClickInterface {

    private lateinit var mainActivityLayout: LinearLayout
    private lateinit var currentFragment : Fragment
    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var currentWeatherFragment: CurrentWeatherFragment
    private lateinit var detailWeatherFragment: DetailWeatherFragment

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentContainerView = findViewById(R.id.currentWeatherFragmentContainerView)
        if ( savedInstanceState == null){
            currentWeatherFragment = CurrentWeatherFragment()
            currentWeatherFragment.setInterface(this)
            currentFragment = currentWeatherFragment
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.currentWeatherFragmentContainerView,currentWeatherFragment)
            }
        }


        mainActivityLayout = findViewById(R.id.mainActivityLayout)
        mainActivityLayout.setOnTouchListener(object : OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                val intent = Intent(applicationContext, ForecastWeatherActivity::class.java)
                startActivity(intent)
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                val intent = Intent(applicationContext, DetailWeatherActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun clicked() {
        changeFragment()
    }

    private fun changeFragment(){
        if (currentFragment == currentWeatherFragment){
            supportFragmentManager.commit {
                detailWeatherFragment = DetailWeatherFragment()
                replace(R.id.currentWeatherFragmentContainerView, detailWeatherFragment)
                setReorderingAllowed(true)
                addToBackStack(null)
            }
        }
    }
}