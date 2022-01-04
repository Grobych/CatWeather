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

class DetailWeatherFragment : Fragment() {
    private lateinit var binding : DetailWeatherFragmentBinding
    private lateinit var viewModel: DetailWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel

    private val gestureDetector = GestureDetector(this.context, DoubleTapGestureDetector())
    private lateinit var clickInterface: ClickInterface
    fun setInterface(click: ClickInterface){
        clickInterface = click
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

    inner class DoubleTapGestureDetector : SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            Log.d("TAG", "Double Tap Detected ...")
            clickInterface.clicked(ClickInterface.To.RIGHT, this@DetailWeatherFragment)
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.d("TAG", "Scroll detected... $distanceX    $distanceY")
            if ((distanceY < 20) && (distanceX > 50)) clickInterface.clicked(ClickInterface.To.RIGHT, this@DetailWeatherFragment)
            if ((distanceY < 20) && (distanceX < -50)) clickInterface.clicked(ClickInterface.To.LEFT, this@DetailWeatherFragment)
            return true
        }
    }
}


