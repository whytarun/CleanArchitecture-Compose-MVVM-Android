package com.android.mvvm.kotlin.data.model.others

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import java.text.DateFormat
import java.util.*

/**
 * Created by hemanth on 17,January,2021
 */
class LocationHelper(appContext: Context?) : LiveData<Location?>() {
     val mContext: Context?

    // bunch of location related apis
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mSettingsClient: SettingsClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mLocationSettingsRequest: LocationSettingsRequest? = null
    private var mLocationCallback: LocationCallback? = null
    private var mCurrentLocation: Location? = null
    private var mLastUpdateTime: String? = null
    private fun buildGoogleApiClient(appContext: Context?) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(appContext!!)
        mSettingsClient = LocationServices.getSettingsClient(appContext)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                // location is received
                Log.e(
                    TAG,
                    "onLocationResult: $locationResult"
                )
                mCurrentLocation = locationResult.lastLocation
                mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                updateLocationUI()
            }
        }
        mLocationRequest = LocationRequest()
        mLocationRequest!!.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        mLocationSettingsRequest = builder.build()
        startLocationUpdates()
    }

    private fun updateLocationUI() {
        if (mCurrentLocation != null) {
            value = mCurrentLocation
        }
    }

    private fun startLocationUpdates() {
        mSettingsClient
                ?.checkLocationSettings(mLocationSettingsRequest)
                ?.addOnSuccessListener {
                    Log.i(TAG, "All location settings are satisfied.")
                    Toast.makeText(mContext, "Started location updates!", Toast.LENGTH_SHORT).show()
                    if (ActivityCompat.checkSelfPermission(
                                    mContext!!,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                    mContext,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@addOnSuccessListener
                    }
                    mFusedLocationClient!!.requestLocationUpdates(
                            mLocationRequest,
                            mLocationCallback, Looper.myLooper()
                    )
                    updateLocationUI()
                }
            ?.addOnFailureListener { e ->
                val statusCode = (e as ApiException).statusCode
                when (statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> Log.i(
                            TAG,
                            "Location settings are not satisfied. Attempting to upgrade " +
                                    "location settings "
                    )
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage = "Location settings are inadequate, and cannot be " +
                                "fixed here. Fix in Settings."
                        Log.e(TAG, errorMessage)
                    }
                }
                updateLocationUI()
            }
    }

    fun stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                ?.removeLocationUpdates(mLocationCallback)
                ?.addOnCompleteListener {
                    Toast.makeText(
                            mContext,
                            "Location updates stopped!",
                            Toast.LENGTH_SHORT
                    ).show()
                }
    }

    companion object {
        private const val TAG = "LocationHelper"
        private var instance: LocationHelper? = null
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS: Long = 5000
        fun getInstance(appContext: Context?): LocationHelper? {
            if (instance == null) {
                instance = LocationHelper(appContext)
            }
            return instance
        }
    }

    init {
        mContext = appContext
        buildGoogleApiClient(appContext)
    }
}