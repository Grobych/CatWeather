package com.globa.catweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.R
import com.globa.catweather.adapters.ViewPagerAdapter
import com.globa.catweather.fragments.CurrentWeatherFragment
import com.globa.catweather.fragments.DetailWeatherFragment
import com.globa.catweather.fragments.ForecastWeatherFragment
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.utils.FragmentSelector
import com.globa.catweather.viewmodels.MainActivityViewModel

class MainActivity : ClickInterface, FragmentActivity() {

    private lateinit var fragmentContainerView: FragmentContainerView

    private lateinit var mainRefreshLayout : SwipeRefreshLayout
    private lateinit var viewModel : MainActivityViewModel

    private lateinit var adapter: FragmentStateAdapter
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        if ( savedInstanceState == null){
            viewModel.init(this)
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add(R.id.currentWeatherFragmentContainerView,viewModel.fragmentSelector.getCurrent())
            }
//        } else{
//            viewModel.setInterface(this)
//        }

        mainRefreshLayout = findViewById(R.id.mainRefreshLayout)
        refreshSwipeListener()

        adapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.mainViewPager)
        viewPager.adapter = adapter

    }

    override fun clicked(direction : ClickInterface.Direction) {
        Log.d("SWIPE", "$direction ")
//        changeFragment(direction)
    }

        private fun refreshSwipeListener(){
        mainRefreshLayout.setOnRefreshListener {
            val fragment = viewPager.findCurrentFragment(supportFragmentManager)
            if (fragment is UpdateInterface) fragment.update()
            mainRefreshLayout.isRefreshing = false
        }
    }

    private fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentByTag("f$currentItem")
    }
}