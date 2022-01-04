package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.globa.catweather.databinding.DetailWeatherFragmentBinding
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.viewmodels.DetailWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel
import android.view.MotionEvent
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.OnTouchListener
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.utils.SwipeGestureDetector

class DetailWeatherFragment : Fragment() {
    private lateinit var binding : DetailWeatherFragmentBinding
    private lateinit var viewModel: DetailWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    private lateinit var gestureDetector: GestureDetector
    private lateinit var clickInterface: ClickInterface
    fun setInterface(click: ClickInterface){
        clickInterface = click
        gestureDetector = GestureDetector(this.context, SwipeGestureDetector(clickInterface))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailWeatherFragmentBinding.inflate(layoutInflater, container, false)
        binding.root.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        weatherUpdateObserver()
        getDetailWeather()
    }

    private fun getDetailWeather() {
        if (NetworkUtil().isNetworkConnected(this.requireContext()))
            viewModel.updateWeather(this.requireContext(), locationViewModel.location.value.toString())
    }

    private fun weatherUpdateObserver() {
        viewModel.detailDayWeather.observe(viewLifecycleOwner, {
                updated -> binding.detailWeather = updated})
    }
}


