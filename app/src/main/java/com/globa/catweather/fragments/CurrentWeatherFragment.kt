package com.globa.catweather.fragments

import android.content.res.Resources
import android.content.res.loader.ResourcesProvider
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.android.volley.toolbox.ImageLoader
import com.globa.catweather.R
import com.globa.catweather.databinding.CurrentWeatherFragmentBinding


class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: CurrentWeatherFragmentBinding
    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel
    lateinit var handler: Handler

    companion object {
        val instance = this
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentWeatherFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CurrentWeatherViewModel::class.java)
        locationViewModel = ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)
        weatherUpdateObserver()
        locationChangedObserver()
        refreshSwipeListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
    private fun weatherUpdateObserver() {
        viewModel.currentT.observe(viewLifecycleOwner, Observer { updatedT -> binding.currentTempTextView.text = updatedT.toString() + "° C" })
        viewModel.condition.observe(viewLifecycleOwner, Observer { updatedCondition -> binding.weatherConditionTextView.text = updatedCondition })
        viewModel.windDirection.observe(viewLifecycleOwner, Observer { updatedWindDirection -> binding.windDirectionTextView.text = updatedWindDirection })
        viewModel.windSpeed.observe(viewLifecycleOwner, Observer { updatedWindSpeed -> binding.windSpeedTextView.text = updatedWindSpeed.toString() })
        viewModel.feelsLike.observe(viewLifecycleOwner, Observer { updatedFeelsLike -> binding.feelsLikeTempTextView.text = updatedFeelsLike.toString() + "° C" }) //TODO: rewrite with degree constant
        viewModel.code.observe(viewLifecycleOwner, Observer { updatedCode -> updateImage(updatedCode) })
    }
    private fun locationChangedObserver(){
        locationViewModel.location.observe(viewLifecycleOwner, Observer { updatedLocation -> viewModel.updateWeather(this.requireContext(),updatedLocation) })
    }
    private fun refreshSwipeListener(){
        binding.weatherRefreshLayout.setOnRefreshListener {
            val city = locationViewModel.location.value ?: return@setOnRefreshListener
            viewModel.updateWeather(this.requireContext(),city)
            binding.weatherRefreshLayout.isRefreshing = false
        }
    }

    private fun updateImage(code : Int){ // TODO: Rewrite with weather code table
        val drawable : Drawable? = when(code){
            1000 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.sunny)
            1003 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.partly_cloudy)
            1006 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.cloudy)
            1009 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.overcast)
            1030 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.mist)
            1117 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.blizzard)
            1135 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.fog)
            1180, 1183 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.light_rain)
            1186, 1189 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.moderate_rain)
            1192, 1195 -> ContextCompat.getDrawable(this.requireContext(), R.drawable.heavy_rain)
            else -> ContextCompat.getDrawable(this.requireContext(), R.drawable.cat_weather_test)
        }
        binding.currentWeatherImage.setImageDrawable(drawable)
    }



}