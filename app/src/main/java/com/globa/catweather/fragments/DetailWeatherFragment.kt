package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.globa.catweather.databinding.DetailWeatherFragmentBinding
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.viewmodels.DetailWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel
import android.view.GestureDetector
import androidx.core.content.ContextCompat
import com.globa.catweather.R
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.models.WeatherCodes
import com.globa.catweather.models.WeatherIcon
import com.globa.catweather.utils.SwipeGestureDetector

class DetailWeatherFragment : Fragment(), UpdateInterface {
    private lateinit var binding : DetailWeatherFragmentBinding
    private lateinit var viewModel: DetailWeatherViewModel
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
        binding = DetailWeatherFragmentBinding.inflate(layoutInflater, container, false)
        binding.root.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        weatherUpdateObserver()
        updateDetailWeather()
    }

    private fun updateDetailWeather() {
        if (NetworkUtil().isNetworkConnected(this.requireContext()))
            viewModel.updateWeather(this.requireContext(), locationViewModel.location.value.toString())
    }

    private fun weatherUpdateObserver() {
        viewModel.detailDayWeather.observe(viewLifecycleOwner, {
                updated ->
            binding.detailWeather = updated
            updateConditionIcon(updated.current.code)
        })
    }

    private fun updateConditionIcon(code : Int){
        val id = WeatherIcon.getByWeatherStatus(WeatherCodes().getByCode(code))
        val icon = if (id != null) {
            ContextCompat.getDrawable(this.requireContext(),id)}
        else {
            ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_cloud_test)}
        binding.detailWeatherConditionIcon.setImageDrawable(icon)
    }

    override fun update() {
        updateDetailWeather()
    }
}


