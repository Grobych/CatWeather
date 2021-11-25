package com.globa.catweather.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globa.catweather.R
import com.globa.catweather.adapters.ForecastAdapter

class ForecastWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = ForecastWeatherFragment()
    }

    private lateinit var viewModel: ForecastWeatherViewModel
    private lateinit var adapter : ForecastAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastWeatherViewModel::class.java)
        recyclerView = view?.findViewById(R.id.forecastWeatherRecyclerView) ?: RecyclerView(this.requireContext())
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        //recyclerView.adapter = ForecastAdapter(viewModel, viewModel.list, this.requireContext())
        viewModel.forecastList.observe(viewLifecycleOwner,{
            Log.i("data",it.toString())
            recyclerView.adapter = ForecastAdapter(viewModel, viewModel.list, this.requireContext()) // TODO: rewrite with notifyDataSetChanged()
        })
        viewModel.updateForecast(this.requireContext(), "Minsk")  // TODO: get from location
//        Log.d("location", "${locationViewModel.location.value.toString()}")

    }

}