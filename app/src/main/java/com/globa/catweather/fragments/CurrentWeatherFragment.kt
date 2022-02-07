package com.globa.catweather.fragments

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.globa.catweather.R
import com.globa.catweather.databinding.CurrentWeatherFragmentBinding
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.models.WeatherCodes
import com.globa.catweather.models.WeatherDrawable
import com.globa.catweather.models.WeatherIcon
import com.globa.catweather.viewmodels.CurrentWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.notifications.CurrentWeatherNotification
import com.globa.catweather.utils.SwipeGestureDetector
import kotlin.random.Random


class CurrentWeatherFragment : Fragment(), UpdateInterface {
    private lateinit var binding: CurrentWeatherFragmentBinding
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    private lateinit var gestureDetector: GestureDetector
    private lateinit var clickInterface: ClickInterface
    fun setInterface(click: ClickInterface){
        clickInterface = click
        gestureDetector = GestureDetector(this.context, SwipeGestureDetector(clickInterface))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrentWeatherFragmentBinding.inflate(layoutInflater, container, false)
        binding.root.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CurrentWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        weatherUpdateObserver()
        locationChangedObserver()
    }

    private fun weatherUpdateObserver() {
        viewModel.currentWeather.observe(viewLifecycleOwner, {
                updated ->
            CurrentWeatherNotification().generateCurrentWeatherNotification(this.requireContext(),updated)
            binding.weather = updated
            updateImage(updated.code)
            updateIcon(updated.code)
        })
    }

    private fun locationChangedObserver(){
        locationViewModel.location.observe(viewLifecycleOwner, { updatedLocation ->
            if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.updateWeather(this.requireContext(),updatedLocation) })
    }

    private fun refreshWeather(){
        val city = locationViewModel.location.value
        if (NetworkUtil().isNetworkConnected(this.requireContext()) && city!= null) {
            viewModel.updateWeather(this.requireContext(),city)
        }
    }

    private fun getByCode(code: Int) : Drawable?{
        val weatherStatus = WeatherCodes().getByCode(code)
        val arrayId = WeatherDrawable.map[weatherStatus]
        return if (arrayId != null){
            val ta = resources.obtainTypedArray(arrayId)
            val id = ta.getResourceId(Random.nextInt(ta.length()),0)
            ta.recycle()
            ContextCompat.getDrawable(this.requireContext(), id)
        }else{
            ContextCompat.getDrawable(this.requireContext(), R.drawable.cat_weather_test)
        }
    }

    private fun updateImage(code : Int){
        val drawable : Drawable?
        val status = WeatherCodes().getByCode(code)
        if ((viewModel.currentType != null) && (viewModel.currentType == status)){
            drawable = viewModel.currentImageDrawable
        } else{
            drawable = getByCode(code)
            viewModel.currentType = status
            viewModel.currentImageDrawable = drawable
        }
        binding.currentWeatherImage.setImageDrawable(drawable)
    }
    private fun updateIcon(code : Int){
        val id = WeatherIcon.getByWeatherStatus(WeatherCodes().getByCode(code))
        val icon = if (id != null) {ContextCompat.getDrawable(this.requireContext(),id)}
            else {ContextCompat.getDrawable(this.requireContext(),R.drawable.ic_cloud_test)}
        binding.currentWeatherConditionIcon.setImageDrawable(icon)
    }

    override fun update() {
        refreshWeather()
    }
}