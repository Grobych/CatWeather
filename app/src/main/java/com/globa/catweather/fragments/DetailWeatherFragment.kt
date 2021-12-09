package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globa.catweather.R
import com.globa.catweather.databinding.DetailWeatherFragmentBinding
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.viewmodels.DetailWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel

class DetailWeatherFragment : Fragment() {
    private lateinit var binding : DetailWeatherFragmentBinding
    private lateinit var viewModel: DetailWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailWeatherFragmentBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        weatherUpdateObserver()
        locationChangedObserver()
        getDetailWeather()
    }

    private fun getDetailWeather() {
        if (NetworkUtil().isNetworkConnected(this.requireContext()))
            viewModel.updateWeather(this.requireContext(), locationViewModel.location.value.toString())
    }

    private fun locationChangedObserver() {
        locationViewModel.location.observe(viewLifecycleOwner, { updatedLocation ->
            if (NetworkUtil().isNetworkConnected(this.requireContext()))
                viewModel.updateWeather(this.requireContext(),updatedLocation) })    }

    private fun weatherUpdateObserver() {
        viewModel.detailDayWeather.observe(viewLifecycleOwner, {
                updated ->
            Log.d("DETAIL WEATHER", "$updated")
            binding.detailWeatherCurrentTemperature.text = "T:  " + updated.current.temp
            binding.detailWeather = updated

        })
    }

}