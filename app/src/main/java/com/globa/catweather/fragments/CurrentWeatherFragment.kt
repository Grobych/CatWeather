package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.globa.catweather.databinding.CurrentWeatherFragmentBinding


class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: CurrentWeatherFragmentBinding
    private lateinit var viewModel: CurrentWeatherViewModel
    val TAG = "Binding"

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
        fragmentTextUpdateObserver()
        //viewModel.updateWeather(this.requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    private fun fragmentTextUpdateObserver() {
        viewModel.currentT.observe(viewLifecycleOwner, Observer { updatedT -> binding.currentTempTextView.text = updatedT.toString() + "° C" })
        viewModel.condition.observe(viewLifecycleOwner, Observer { updatedCondition -> binding.weatherConditionTextView.text = updatedCondition })
        viewModel.windDirection.observe(viewLifecycleOwner, Observer { updatedWindDirection -> binding.windDirectionTextView.text = updatedWindDirection })
        viewModel.windSpeed.observe(viewLifecycleOwner, Observer { updatedWindSpeed -> binding.windSpeedTextView.text = updatedWindSpeed.toString() })
        viewModel.feelsLike.observe(viewLifecycleOwner, Observer { updatedFeelsLike -> binding.feelsLikeTempTextView.text = updatedFeelsLike.toString() + "° C" }) //TODO: rewrite with degree constant
        val locationViewModel = ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)
        Log.d("VIEW MODEL", "$locationViewModel")
        locationViewModel.location.observe(viewLifecycleOwner, Observer { updatedLocation -> viewModel.updateWeather(this.requireContext(),updatedLocation) })
    }


}