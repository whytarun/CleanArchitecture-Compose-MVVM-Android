package com.android.mvvm.kotlin.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

/**
 * Created by hemanth on 17,January,2021
 */
object LocationUtils {

    private fun LocationUtils() {
        // This class is not publicly instantiable
    }

    /**
     * Check if location permissions are granted.
     */
    fun hasLocationPermission(context: Context?): Boolean {
        return ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Check if location is enabled on the device.
     */
    fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0
        val locationProviders: String
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            locationMode = try {
                Settings.Secure.getInt(context.contentResolver, Settings.Secure.LOCATION_MODE)
            } catch (e: SettingNotFoundException) {
                return false
            }
            locationMode != Settings.Secure.LOCATION_MODE_OFF
        } else {
            locationProviders = Settings.Secure.getString(
                    context.contentResolver,
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED
            )
            !TextUtils.isEmpty(locationProviders)
        }
    }

    /**
     * Check if google play service is available on device.
     */
    fun isGooglePlayServicesAvailable(context: Context?): Boolean {
        val result = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)

        // TODO: Handle other possible success types.
        return result == ConnectionResult.SUCCESS || result == ConnectionResult.SERVICE_UPDATING
    }

}
