package com.android.mvvm.kotlin.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Parcel
import android.view.View
import androidx.core.content.ContextCompat
import com.android.mvvm.kotlin.BR
import com.android.mvvm.kotlin.R
import com.android.mvvm.kotlin.databinding.FragmentLocationUpdatesBinding
import com.android.mvvm.kotlin.di.component.FragmentComponent
import com.android.mvvm.kotlin.ui.base.BaseFragment
import com.android.mvvm.kotlin.utils.LocationUtils
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import java.util.*
import javax.inject.Inject

/**
 * Created by hemanth on 17,January,2021
 */
@SuppressLint("ParcelCreator")
class LocationFragment @Inject constructor():
    BaseFragment<FragmentLocationUpdatesBinding?, LocationViewModel?>(), LocationNavigator,
    OnMapReadyCallback {
    var fragmentLocationUpdatesBinding: FragmentLocationUpdatesBinding? = null
    var locationArrayList: ArrayList<Location?>? = ArrayList()
     var locationObj: Location? = null
     var map: GoogleMap? = null
     val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return com.android.mvvm.kotlin.R.layout.fragment_location_updates
    }

//    fun onAttach(context: Context?) {
//        super.onAttach(context)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel?.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentLocationUpdatesBinding = getViewDataBinding()
        setupViews()
    }

   override fun performDependencyInjection(buildComponent: FragmentComponent?) {
       buildComponent?.inject(this)
    }

    private fun setupViews() {
        fragmentLocationUpdatesBinding?.buttonStartLocation?.setOnClickListener({ v ->
            if (!LocationUtils.hasLocationPermission(getContext())) {
                getBaseActivity()?.requestPermissionsSafely(
                    locationPermissions,
                    REQUEST_CHECK_SETTINGS
                )
            } else {
                subscribeToLocationUpdate()
            }
        })
        fragmentLocationUpdatesBinding?.buttonStopLocation?.setOnClickListener({ v -> stopLocationUpdates() })

        //Maps
        val mapFragment = getChildFragmentManager().findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeToLocationUpdate() {
//        mViewModel.getLocationHelper(getContext()).observe(context!!, { location ->
//            Toast.makeText(getContext(), "on changed called", Toast.LENGTH_SHORT).show()
//            locationObj = location
//            getViewDataBinding().locationText.setText(
//                location.getLatitude().toString() + " " + location.getLongitude()
//            )
//            plotMarkers(locationObj)
//        })
    }

//    private fun plotMarkers(locationObj: Location?) {
//        if (map != null) {
//            val india = LatLng(locationObj!!.latitude, locationObj.longitude)
//            map!!.addMarker(MarkerOptions().position(india))
//            val zoom = LatLng(12f)
//            map!!.moveCamera(CameraUpdateFactory.newLatLng(india))
//            map!!.animateCamera(zoom)
//            locationArrayList!!.add(locationObj)
//            Log.d("Location list", "plotMarkers: " + locationArrayList!!.size)
//
//            //Draw Line
//            var singleLatLong: LatLng? = null
//            val pnts = ArrayList<LatLng>()
//            if (locationArrayList != null) {
//                for (i in locationArrayList!!.indices) {
//                    val routePoint1Lat = locationArrayList!![i]!!.latitude
//                    val routePoint2Long = locationArrayList!![i]!!.longitude
//                    singleLatLong = LatLng(
//                        routePoint1Lat,
//                        routePoint2Long
//                    )
//                    Int(singleLatLong)
//                    map!!.addPolyline(
//                        PolylineOptions().addAll(pnts)
//                            .width(8f)
//                            .color(Color.BLUE)
//                            .zIndex(30f)
//                    )
//                }
//            }
//        }
//    }

    override  fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (getContext()?.let {
                            ContextCompat.checkSelfPermission(
                                it,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        }
                        == PackageManager.PERMISSION_GRANTED
                    ) {

                        //Request location updates:
                        subscribeToLocationUpdate()
                    }
                } else {
                    // permission denied
                }
                return
            }
        }
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    private fun stopLocationUpdates() {
        mViewModel?.getLocationHelper(getContext())?.stopLocationUpdates()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
    }

    override fun goBack() {
        getBaseActivity()?.onFragmentDetached(TAG)
    }

    companion object {
        val TAG = LocationFragment::class.java.simpleName
        private const val REQUEST_CHECK_SETTINGS = 100
        fun newInstance(): LocationFragment {
            val args = Bundle()
            val locationFragment = LocationFragment()
            locationFragment.setArguments(args)
            return locationFragment
        }
    }


}
