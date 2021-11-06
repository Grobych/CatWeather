package com.globa.catweather.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.globa.catweather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationFragment : Fragment() {
    val LOCATION_PERMISSION_CODE = 1000

    companion object {
        fun newInstance() = LocationFragment()
    }

    private lateinit var viewModel: LocationViewModel
    lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        viewModel = ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)
        Log.d("VIEW MODEL", "$viewModel")

        if (checkPermission(this.requireContext(), this.requireActivity())){
            viewModel.fusedLocationClient = fusedLocationClient
            viewModel.location.observe(viewLifecycleOwner, Observer {
                    updatedLocation ->
                val textView : TextView = requireView().findViewById<TextView>(R.id.locationTextView)
                textView.text = updatedLocation
            })
            viewModel.updateLocation()
        }
        //TODO() : add right check permissions
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun checkPermission(context : Context, activity : Activity) : Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_CODE
            );
            false
        } else true
    }

}