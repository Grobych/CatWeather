package com.globa.catweather.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import androidx.fragment.app.Fragment
import com.globa.catweather.R
import com.globa.catweather.utils.KeyboardUtil
import com.globa.catweather.viewmodels.LocationViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.Intent
import android.content.DialogInterface
import android.location.LocationManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.globa.catweather.utils.LocationPermissionsUtil
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

    @RequiresApi(Build.VERSION_CODES.Q)
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

        if (LocationPermissionsUtil.hasLocationPermissions(context!!)) requestLocation()
    }

    private fun requestPermissions(){
        Log.d("LOCATION PERMISSION", "request permissions")
        Log.d("LOCATION PERMISSION", "$context $activity")
        LocationPermissionsUtil.requestPermissions(context!!,activity!!)
    }

    fun requestLocation(){
        viewModel.locationRequestInit(context!!)
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
                .setPositiveButton(R.string.open_location_settings
                ) { _, _ ->
                    context!!.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )
                }
                .setNegativeButton(R.string.Cancel, null)
                .show()
        }
    }
}