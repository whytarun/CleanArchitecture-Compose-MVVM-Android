package com.android.mvvm.kotlin.ui.location

import android.content.Context
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.data.model.others.LocationHelper
import com.android.mvvm.kotlin.ui.base.BaseViewModel
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider

/**
 * Created by hemanth on 17,January,2021
 */
class LocationViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) :
    BaseViewModel<LocationNavigator?>(dataManager!!, schedulerProvider!!) {
    fun getLocationHelper(context: Context?): LocationHelper {
        return LocationHelper.getInstance(context)!!
    }

    fun onNavBackClick() {
        getNavigator()?.goBack()
    }
}
