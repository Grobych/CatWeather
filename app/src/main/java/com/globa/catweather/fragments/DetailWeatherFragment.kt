package com.globa.catweather.fragments

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.globa.catweather.databinding.DetailWeatherFragmentBinding
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.viewmodels.DetailWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel
import androidx.core.content.ContextCompat
import com.globa.catweather.R
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.models.WeatherCodes
import com.globa.catweather.models.WeatherIcon

class DetailWeatherFragment : Fragment(), UpdateInterface {
    private lateinit var binding : DetailWeatherFragmentBinding
    private lateinit var viewModel: DetailWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailWeatherFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        weatherUpdateObserver()
        updateDetailWeather()
        locationChangedObserver()
    }

    private fun updateDetailWeather() {
        if (NetworkUtil().isNetworkConnected(this.requireContext()))
            viewModel.updateWeather(this.requireContext(), locationViewModel.location.value.toString())
    }

    private fun weatherUpdateObserver() {
        viewModel.detailDayWeather.observe(viewLifecycleOwner, {
                updated ->
            binding.skeletonLinearLayout.hideSkeleton()
            binding.detailWeather = updated
            updateConditionIcon(updated.current.code)
            updateTemperatureIcon(updated.current.temp)
        })
    }

    private fun locationChangedObserver(){
        locationViewModel.location.observe(viewLifecycleOwner, { updatedLocation ->
            if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.updateWeather(this.requireContext(),updatedLocation) })
    }
    private fun updateConditionIcon(code : Int){
        val id = WeatherIcon.getByWeatherStatus(WeatherCodes().getByCode(code))
        val icon = if (id != null) {
            ContextCompat.getDrawable(this.requireContext(),id)}
        else {
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_cloud_test)}
        binding.detailWeatherConditionIcon.setImageDrawable(icon)
    }

    private fun updateTemperatureIcon(t : Double){
        lateinit var icon : Drawable
        when (t){
            in Double.MIN_VALUE..-20.0 -> icon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_empty_temp)!!
            in -20.0..5.0 -> icon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_temp_low)!!
            in 5.0..25.0 -> icon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_temp_moderate)!!
            in 25.0..Double.MAX_VALUE -> icon = ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_temp_high)!!
        }
        binding.detailWeatherTemperatureIcon?.setImageDrawable(icon)
    }

    override fun update() {
        updateDetailWeather()
    }
}


