package com.android.mvvm.kotlin.ui.main

/**
 * Created by hemanth on 17,January,2021
 */
open interface MainNavigator {
    fun handleError(throwable: Throwable?)
    fun openLoginActivity()
}
