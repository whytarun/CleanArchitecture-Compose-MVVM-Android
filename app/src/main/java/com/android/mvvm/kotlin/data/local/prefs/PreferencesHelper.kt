package com.android.mvvm.kotlin.data.local.prefs

import com.android.mvvm.kotlin.data.DataManager

/**
 * Created by hemanth on 17,January,2021
 */
open interface PreferencesHelper {
    fun getAccessToken(): String?
    fun setAccessToken(accessToken: String?)
    fun getCurrentUserEmail(): String?
    fun setCurrentUserEmail(email: String?)
    fun getCurrentUserId(): Long?
    fun setCurrentUserId(userId: Long?)
    fun getCurrentUserLoggedInMode(): Int
    fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode?)
    fun getCurrentUserName(): String?
    fun setCurrentUserName(userName: String?)
    fun getCurrentUserProfilePicUrl(): String?
    fun setCurrentUserProfilePicUrl(profilePicUrl: String?)
}
