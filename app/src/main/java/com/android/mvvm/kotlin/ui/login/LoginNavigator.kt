package com.android.mvvm.kotlin.ui.login

/**
 * Created by hemanth on 16,January,2021
 */
open interface LoginNavigator {
    fun handleError(throwable: Throwable?)
    fun login()
    fun openMainActivity()
}