package com.globa.catweather.activities

import android.os.Bundle
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.globa.catweather.R
import com.globa.catweather.adapters.ViewPagerAdapter
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.viewmodels.MainActivityViewModel

class MainActivity : FragmentActivity() {

    private lateinit var mainRefreshLayout : SwipeRefreshLayout
    private lateinit var viewModel : MainActivityViewModel

    private lateinit var adapter: FragmentStateAdapter
    private lateinit var viewPager : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        mainRefreshLayout = findViewById(R.id.mainRefreshLayout)
        refreshSwipeListener()

        adapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.mainViewPager)
        viewPager.adapter = adapter

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