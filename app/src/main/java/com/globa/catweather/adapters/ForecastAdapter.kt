package com.globa.catweather.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.globa.catweather.R
import com.globa.catweather.databinding.ForecastWeatherFragmentItemBinding
import com.globa.catweather.viewmodels.ForecastWeatherViewModel
import com.globa.catweather.models.ForecastWeather
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.globa.catweather.models.WeatherCodes
import com.globa.catweather.models.WeatherIcon
import java.text.DateFormat
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class ForecastAdapter(val viewModel: ForecastWeatherViewModel, private val items: MutableLiveData<ArrayList<ForecastWeather>>, val context: Context) : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ForecastWeatherFragmentItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.forecast_weather_fragment_item, parent, false)
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items.value!![position])
    }

    override fun getItemCount(): Int {
        return items.value!!.size
    }


    inner class ForecastViewHolder(private val binding : ForecastWeatherFragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(forecastWeather : ForecastWeather){
            Log.i("Binding", "$forecastWeather")
            binding.forecast = forecastWeather
            binding.forecastWeatherDataTextView.text = formatDate(forecastWeather.date)
            updateIcon(forecastWeather.code)
        }

        private fun formatDate(date : Date) : String{
            return DateFormat.getDateInstance().format(date,StringBuffer(), FieldPosition(DateFormat.DATE_FIELD)).toString()
        }

        private fun updateIcon(code : Int){
            val id = WeatherIcon.getByWeatherStatus(WeatherCodes().getByCode(code))
            val icon = if (id != null) {
                ContextCompat.getDrawable(context,id)}
            else {
                ContextCompat.getDrawable(context,R.drawable.ic_cloud_test)}
            binding.forecastWeatherItemConditionIcon.setImageDrawable(icon)
        }
    }
}