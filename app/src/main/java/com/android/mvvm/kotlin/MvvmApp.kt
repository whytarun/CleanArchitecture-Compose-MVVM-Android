package com.android.mvvm.kotlin

import android.app.Application
import androidx.databinding.library.BuildConfig
import com.android.mvvm.kotlin.di.component.AppComponent
import com.android.mvvm.kotlin.di.component.DaggerAppComponent
import com.android.mvvm.kotlin.utils.AppLogger
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor


/**
 * Created by hemanth on 17,January,2021
 */
class MvvmApp : Application() {
    var appComponent: AppComponent? = null
    override fun onCreate() {
        super.onCreate()
        init()
        AndroidNetworking.initialize(applicationContext)
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        }
    }
    fun init() {

        appComponent = DaggerAppComponent.builder()
            .application(this)
            ?.build()
        appComponent!!.inject(this)
        AppLogger.init()
    }
}