package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globa.catweather.R

class ForecastWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastWeatherFragment()
    }

    private lateinit var viewModel: ForecastWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastWeatherViewModel::class.java)


        viewModel.updateForecast(this.requireContext(), "Minsk")  // TODO: get from location

    }

}