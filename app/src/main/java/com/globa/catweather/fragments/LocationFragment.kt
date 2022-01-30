package com.globa.catweather.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.globa.catweather.R
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.viewmodels.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationFragment : Fragment() {
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

        viewModel.fusedLocationClient = fusedLocationClient
        viewModel.location.observe(viewLifecycleOwner, {
                updatedLocation ->
            val textView : TextView = requireView().findViewById(R.id.locationTextView)
            textView.text = updatedLocation
        })

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        viewModel.locationRequestInit()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                    } else -> {
                    Toast.makeText(this.requireContext(),"No permissions",Toast.LENGTH_SHORT).show()
                }
                }
            } else{
                if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                        this.requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), this.requireContext().resources.getInteger(R.integer.location_permission_code))
                } else{
                    viewModel.locationRequestInit()
                }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if ((requestCode == R.integer.location_permission_code) && (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION))){
            Log.d(tag,"Permission granted")
            if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.locationRequestInit()
        }
    }

}