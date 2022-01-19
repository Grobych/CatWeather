package com.globa.catweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.R
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity(), ClickInterface {

    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var mainRefreshLayout : SwipeRefreshLayout
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        fragmentContainerView = findViewById(R.id.currentWeatherFragmentContainerView)
        if ( savedInstanceState == null){
            viewModel.init(this)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.currentWeatherFragmentContainerView,viewModel.fragmentSelector.getCurrent())
            }
        } else{
            viewModel.setInterface(this)
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
            replace(R.id.currentWeatherFragmentContainerView, viewModel.fragmentSelector.to(direction))
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        clearBackStack()
        super.onBackPressed()
    }
    private fun clearBackStack() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            val first = supportFragmentManager.getBackStackEntryAt(1)
            supportFragmentManager.popBackStack(first.id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

        private fun refreshSwipeListener(){
        mainRefreshLayout.setOnRefreshListener {
            if (viewModel.fragmentSelector.getCurrent() is CurrentWeatherFragment) (viewModel.fragmentSelector.getCurrent() as CurrentWeatherFragment).refreshWeather()
            mainRefreshLayout.isRefreshing = false
        }
    }
}