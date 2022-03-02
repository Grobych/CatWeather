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
import com.globa.catweather.interfaces.UpdateInterface
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.viewmodels.ForecastWeatherViewModel
import com.globa.catweather.viewmodels.LocationViewModel

class ForecastWeatherFragment : Fragment(), UpdateInterface {

    private lateinit var viewModel: ForecastWeatherViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ForecastWeatherViewModel::class.java]
        locationViewModel = LocationViewModel.getInstance(this.requireActivity().application)
        recyclerView = view.findViewById(R.id.forecastWeatherRecyclerView) ?: RecyclerView(this.requireContext())
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        viewModel.forecastList.observe(viewLifecycleOwner,{
            Log.i("data",it.toString())
            recyclerView.adapter = ForecastAdapter(viewModel, viewModel.forecastList, this.requireContext()) // TODO: rewrite with notifyDataSetChanged()
        })
        update()
        locationChangedObserver()
    }

    override fun update() {
        viewModel.updateForecast(this.requireContext(),locationViewModel.location.value.toString())
    }

    private fun locationChangedObserver(){
        locationViewModel.location.observe(viewLifecycleOwner, { updatedLocation ->
            if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.updateForecast(this.requireContext(),updatedLocation) })
    }

}