package com.globa.catweather.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.globa.catweather.R
import com.globa.catweather.databinding.ForecastWeatherFragmentItemBinding
import com.globa.catweather.fragments.ForecastWeatherViewModel
import com.globa.catweather.models.ForecastWeather
import androidx.databinding.DataBindingUtil

class ForecastAdapter(val viewModel: ForecastWeatherViewModel, private val items: ArrayList<ForecastWeather>, val context: Context) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ForecastWeatherFragmentItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.forecast_weather_fragment_item, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ForecastViewHolder(private val binding : ForecastWeatherFragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastWeather : ForecastWeather){
            Log.i("Binding", "$forecastWeather")
            binding.forecast = forecastWeather
            binding.forecastMinTemperatureTextView.text = forecastWeather.minT.toString()
            binding.forecastMaxTemperatureTextView.text = forecastWeather.maxT.toString()
            binding.forecastMaxWindSpeedTextView.text = forecastWeather.maxWindSpeed.toString()
            binding.forecastConditionTextView.text = forecastWeather.condition
        }
    }
}