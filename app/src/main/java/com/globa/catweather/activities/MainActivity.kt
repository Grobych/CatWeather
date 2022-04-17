package com.globa.catweather.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.globa.catweather.R
import com.globa.catweather.adapters.ViewPagerAdapter
import com.globa.catweather.fragments.LocationFragment
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.services.UpdateWorker
import com.globa.catweather.viewmodels.MainActivityViewModel
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.concurrent.TimeUnit

class MainActivity : FragmentActivity(), EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private lateinit var mainRefreshLayout : SwipeRefreshLayout
    private lateinit var viewModel : MainActivityViewModel

    private lateinit var adapter: FragmentStateAdapter
    private lateinit var viewPager : ViewPager2

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        mainRefreshLayout = findViewById(R.id.mainRefreshLayout)
        refreshSwipeListener()

        adapter = ViewPagerAdapter(this)
        viewPager = findViewById(R.id.mainViewPager)
        viewPager.adapter = adapter

        val aboutIconImageView = findViewById<ImageView>(R.id.aboutIconImageView)
        aboutIconImageView.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }

        private fun refreshSwipeListener(){
        mainRefreshLayout.setOnRefreshListener {
            val fragment = viewPager.findCurrentFragment(supportFragmentManager)
            if (fragment is UpdateInterface) fragment.update()
            mainRefreshLayout.isRefreshing = false
        }
    }

    override fun onStop() {
        val workRequest = PeriodicWorkRequestBuilder<UpdateWorker>(30,TimeUnit.MINUTES)

        Log.d("WORKER", "On stop")
        WorkManager.getInstance(this).enqueue(workRequest.build())

        super.onStop()
    }

    private fun ViewPager2.findCurrentFragment(fragmentManager: FragmentManager): Fragment? {
        return fragmentManager.findFragmentByTag("f$currentItem")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("LOCATION PERMISSION", "on request permissions result: $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("LOCATION PERMISSION","on permission granted ${perms[0]}")
        val locationFragmentContainerView = findViewById<FragmentContainerView>(R.id.locationFragmentContainerView)
        locationFragmentContainerView.getFragment<LocationFragment>().requestLocation()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d("LOCATION PERMISSION", "on permissions denied ${perms[0]}")
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onRationaleAccepted(requestCode:Int) {
        Log.d("LOCATION PERMISSION", "onRationaleAccepted: $requestCode")
        val locationFragmentContainerView = findViewById<FragmentContainerView>(R.id.locationFragmentContainerView)
        locationFragmentContainerView.getFragment<LocationFragment>().requestLocation()
    }
    override fun onRationaleDenied(requestCode:Int) {
        Log.d("LOCATION PERMISSION", "onRationaleDenied: $requestCode")
    }
}