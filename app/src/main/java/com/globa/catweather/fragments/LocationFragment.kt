package com.globa.catweather.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.globa.catweather.R
import com.globa.catweather.network.NetworkUtil
import com.globa.catweather.utils.KeyboardUtil
import com.globa.catweather.viewmodels.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.Intent

import android.content.DialogInterface

import android.location.LocationManager
import android.provider.Settings
import androidx.lifecycle.ViewModelProvider
import com.globa.catweather.services.LocationUpdateService
import com.globa.catweather.viewmodels.DetailWeatherViewModel
import java.lang.Exception


class LocationFragment : Fragment() {
    private lateinit var viewModel: LocationViewModel
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private lateinit var textView: TextView
    private lateinit var editText: EditText

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

        textView = view.findViewById(R.id.locationTextView) as TextView
        editText = view.findViewById(R.id.locationTextEdit) as EditText

        setListeners()
        requestPermissions()
        checkLocationProvider()
        setObserver()

//        activity!!.startService(Intent(context,LocationUpdateService::class.java))
    }

    private fun requestPermissions(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        viewModel.locationRequest()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        // Only approximate location access granted.
                    }
                    else -> {
                        Toast.makeText(this.requireContext(), "No permissions", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                if (ActivityCompat.checkSelfPermission(
                        this.requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this.requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        this.requireContext().resources.getInteger(R.integer.location_permission_code)
                    )
                } else {
                    viewModel.locationRequest()
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if ((requestCode == R.integer.location_permission_code) && (permissions.contains(Manifest.permission.ACCESS_FINE_LOCATION))){
            Log.d(tag,"Permission granted")
            if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.locationRequest()
        }
    }
    private fun setListeners(){
        val layout = view?.findViewById(R.id.locationLinearLayout) as LinearLayout
        layout.setOnClickListener {
            if (editText.visibility == GONE){
                textView.visibility = GONE
                editText.visibility = VISIBLE
                editText.text.clear()
                editText.requestFocus()
            }
        }
        editText.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            val input : String
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                input = v.text.toString()
                Log.d("LOCATION INPUT", "Post $input")
                viewModel.location.postValue(input)
                KeyboardUtil.closeKeyboard(requireActivity())
                editText.clearFocus()
                editText.visibility = GONE
                textView.visibility = VISIBLE
                KeyboardUtil.openKeyboard(requireActivity())
                return@OnEditorActionListener true
            }
            false // pass on to other listeners.
        })
    }
    private fun setObserver(){
        viewModel.location.observe(viewLifecycleOwner, { updatedLocation ->
            textView.text = updatedLocation
        })
    }
    private fun checkLocationProvider(){
        val lm = context!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        if (!gpsEnabled && !networkEnabled) {
            // notify user
            AlertDialog.Builder(context)
                .setMessage(R.string.gps_network_not_enabled)
                .setPositiveButton(R.string.open_location_settings,
                    DialogInterface.OnClickListener { paramDialogInterface, paramInt ->
                        context!!.startActivity(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        )
                    })
                .setNegativeButton(R.string.Cancel, null)
                .show()
        }
    }

    fun locationRequest(){
        viewModel.locationRequest()
    }
}