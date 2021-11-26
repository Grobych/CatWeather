package com.globa.catweather.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.globa.catweather.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationFragment : Fragment() {
//    private val locationPermissionCode = requireContext().resources.getInteger(R.integer.locationPermissionCode)

    private lateinit var viewModel: LocationViewModel
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as Activity)

        viewModel = LocationViewModel.getInstance(this.requireActivity().application)
        if (checkPermission(this.requireContext(), this.requireActivity())){
            viewModel.fusedLocationClient = fusedLocationClient
            viewModel.location.observe(viewLifecycleOwner, {
                    updatedLocation ->
                val textView : TextView = requireView().findViewById(R.id.locationTextView)
                textView.text = updatedLocation
            })
            viewModel.locationRequestInit()
        }
        //TODO() : add right check permissions
    }

    private fun checkPermission(context : Context, activity : Activity) : Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), context.resources.getInteger(R.integer.location_permission_code)
            )
            false
        } else true
    }

}