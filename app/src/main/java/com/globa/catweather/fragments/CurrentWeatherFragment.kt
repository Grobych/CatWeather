package com.globa.catweather.fragments

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.globa.catweather.R
import com.globa.catweather.databinding.CurrentWeatherFragmentBinding
import com.globa.catweather.models.WeatherCodes
import com.globa.catweather.models.WeatherDrawable
import com.globa.catweather.viewmodels.CurrentWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel
import com.globa.catweather.network.NetworkUtil
import kotlin.random.Random


class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: CurrentWeatherFragmentBinding
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrentWeatherFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CurrentWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        weatherUpdateObserver()
        locationChangedObserver()
        refreshSwipeListener()
    }

    private fun weatherUpdateObserver() {
        viewModel.currentT.observe(viewLifecycleOwner, { updatedT -> binding.currentTempTextView.text = requireContext().resources.getString(R.string.current_weather_temperature_template,updatedT) })
        viewModel.condition.observe(viewLifecycleOwner, { updatedCondition -> binding.weatherConditionTextView.text = updatedCondition })
        viewModel.windDirection.observe(viewLifecycleOwner, { updatedWindDirection -> binding.windDirectionTextView.text = updatedWindDirection })
        viewModel.windSpeed.observe(viewLifecycleOwner, { updatedWindSpeed -> binding.windSpeedTextView.text = updatedWindSpeed.toString() })
        viewModel.feelsLike.observe(viewLifecycleOwner, { updatedFeelsLike -> binding.feelsLikeTempTextView.text = requireContext().resources.getString(R.string.current_weather_feelslike_template,updatedFeelsLike) })
        viewModel.code.observe(viewLifecycleOwner, { updatedCode -> updateImage(updatedCode) })
    }
    private fun locationChangedObserver(){
        locationViewModel.location.observe(viewLifecycleOwner, { updatedLocation ->
            if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.updateWeather(this.requireContext(),updatedLocation) })
    }
    private fun refreshSwipeListener(){
        binding.weatherRefreshLayout.setOnRefreshListener {
            val city = locationViewModel.location.value
            if (NetworkUtil().isNetworkConnected(this.requireContext()) && city!= null) {
                viewModel.updateWeather(this.requireContext(),city)
            }
            binding.weatherRefreshLayout.isRefreshing = false
        }
    }

    private fun getByCode(code: Int) : Drawable?{
        val weatherStatus = WeatherCodes().getByCode(code)
        val arrayId = WeatherDrawable().map[weatherStatus]
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
        val drawable = getByCode(code)
        binding.currentWeatherImage.setImageDrawable(drawable)
    }
}