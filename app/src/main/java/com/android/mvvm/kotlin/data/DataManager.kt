package com.android.mvvm.kotlin.data

import com.android.mvvm.kotlin.data.local.db.DbHelper
import com.android.mvvm.kotlin.data.local.prefs.PreferencesHelper
import com.android.mvvm.kotlin.data.model.db.Question
import com.android.mvvm.kotlin.data.model.others.QuestionCardData
import com.android.mvvm.kotlin.data.remote.ApiHelper
import io.reactivex.Observable

/**
 * Created by hemanth on 16,January,2021
 */
open interface DataManager : DbHelper, PreferencesHelper, ApiHelper {
    fun getQuestionCardData(): Observable<List<QuestionCardData?>?>?
    fun seedDatabaseOptions(): Observable<Boolean>
    fun seedDatabaseQuestions(): Observable<Boolean>
    fun setUserAsLoggedOut()
    fun updateApiHeader(userId: Long?, accessToken: String?)
    fun updateUserInfo(
        accessToken: String?,
        userId: Long?,
        loggedInMode: LoggedInMode?,
        userName: String?,
        email: String?,
        profilePicPath: String?
    )

    enum class LoggedInMode(val type: Int) {
        LOGGED_IN_MODE_LOGGED_OUT(0), LOGGED_IN_MODE_GOOGLE(1), LOGGED_IN_MODE_FB(2), LOGGED_IN_MODE_SERVER(
            3
        );

    }
}