package com.globa.catweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.DetailWeatherFragment
import com.globa.catweather.fragments.ForecastWeatherFragment
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.utils.FragmentSelector
import com.globa.catweather.R

class MainActivity : AppCompatActivity(), ClickInterface {

    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var currentWeatherFragment: CurrentWeatherFragment
    private lateinit var detailWeatherFragment: DetailWeatherFragment
    private lateinit var forecastWeatherFragment : ForecastWeatherFragment

    private lateinit var fragmentSelector: FragmentSelector
    private lateinit var mainRefreshLayout : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentWeatherFragment = CurrentWeatherFragment()
        currentWeatherFragment.setInterface(this)
        detailWeatherFragment = DetailWeatherFragment()
        detailWeatherFragment.setInterface(this)
        forecastWeatherFragment = ForecastWeatherFragment()
        forecastWeatherFragment.setInterface(this)
        fragmentSelector = FragmentSelector(listOf(currentWeatherFragment,detailWeatherFragment,forecastWeatherFragment))

        fragmentContainerView = findViewById(R.id.currentWeatherFragmentContainerView)
        if ( savedInstanceState == null){
            val fragment = fragmentSelector.getCurrent()
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.currentWeatherFragmentContainerView,fragment)
            }
        }

        mainRefreshLayout = findViewById(R.id.mainRefreshLayout)
        refreshSwipeListener()
    }

    override fun clicked(direction : ClickInterface.Direction) {
        Log.d("SWIPE", "$direction ")
        changeFragment(direction)
    }

    private fun changeFragment(direction: ClickInterface.Direction){
        supportFragmentManager.commit {
            when (direction){
                ClickInterface.Direction.RIGHT -> setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out)
                ClickInterface.Direction.LEFT -> setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out)
            }
            replace(R.id.currentWeatherFragmentContainerView, fragmentSelector.to(direction))
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

        private fun refreshSwipeListener(){
        mainRefreshLayout.setOnRefreshListener {
            currentWeatherFragment.refreshWeather()
            mainRefreshLayout.isRefreshing = false
        }
    }
}