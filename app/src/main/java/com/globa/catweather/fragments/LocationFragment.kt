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
import com.globa.catweather.services.LocationBackgroundService
import com.globa.catweather.utils.LocationPermissionsUtil
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.lang.Exception


class LocationFragment : Fragment(), EasyPermissions.PermissionCallbacks {
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

        viewModel.locationRequestInit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val res = context?.startForegroundService((Intent(context,
                LocationBackgroundService::class.java)))
            Log.d("SERVICE", "$res")
        } else{
            context!!.startService(Intent(context, LocationBackgroundService::class.java))
        }
    }

    private fun requestPermissions(){
        LocationPermissionsUtil.requestPermissions(context!!,activity!!)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(tag,"Permission granted")
//        if (NetworkUtil().isNetworkConnected(this.requireContext())) viewModel.locationRequestInit()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermissions()
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
                    DialogInterface.OnClickListener { _, paramInt ->
                        context!!.startActivity(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        )
                    })
                .setNegativeButton(R.string.Cancel, null)
                .show()
        }
    }
}