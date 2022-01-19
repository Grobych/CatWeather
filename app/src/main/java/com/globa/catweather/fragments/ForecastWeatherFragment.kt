package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globa.catweather.R
import com.globa.catweather.adapters.ForecastAdapter
import com.globa.catweather.interfaces.ClickInterface
import com.globa.catweather.utils.SwipeGestureDetector
import com.globa.catweather.viewmodels.ForecastWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel

class ForecastWeatherFragment : Fragment() {

    private lateinit var viewModel: ForecastWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var recyclerView: RecyclerView

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
    ): View? {
        val root = inflater.inflate(R.layout.forecast_weather_fragment, container, false)
        root.setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            v.performClick()
            true
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ForecastWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        recyclerView = view?.findViewById(R.id.forecastWeatherRecyclerView) ?: RecyclerView(this.requireContext())
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        viewModel.forecastList.observe(viewLifecycleOwner,{
            Log.i("data",it.toString())
            recyclerView.adapter = ForecastAdapter(viewModel, viewModel.forecastList, this.requireContext()) // TODO: rewrite with notifyDataSetChanged()
        })
        viewModel.updateForecast(this.requireContext(), locationViewModel.location.value.toString())
    }

}