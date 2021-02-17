package com.android.mvvm.kotlin.ui.login

import android.text.TextUtils
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.data.model.api.LoginRequest
import com.android.mvvm.kotlin.ui.base.BaseViewModel
import com.android.mvvm.kotlin.utils.CommonUtils
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider

/**
 * Created by hemanth on 16,January,2021
 */
class LoginViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) :
    BaseViewModel<LoginNavigator?>(dataManager!!, schedulerProvider!!) {


    fun isEmailAndPasswordValid(email: String?, password: String?): Boolean {
        // validate email and password
        if (TextUtils.isEmpty(email)) {
            return false
        }
        if (!CommonUtils.isEmailValid(email)) {
            return false
        }
        return if (TextUtils.isEmpty(password)) {
            false
        } else true
    }

    fun login(email: String?, password: String?) {
        setIsLoading(true)
        getCompositeDisposable().add(
            getDataManager()
                .doServerLoginApiCall(LoginRequest.ServerLoginRequest(email, password))
                .doOnSuccess({ response ->
                    getDataManager()
                        .updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl()
                        )
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({ response ->
                    setIsLoading(false)
                    getNavigator()?.openMainActivity()
                }, { throwable ->
                    setIsLoading(false)
                    getNavigator()?.handleError(throwable)
                })
        )
    }

    fun onFbLoginClick() {
        setIsLoading(true)
        getCompositeDisposable().add(
            getDataManager()
                .doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest("test3", "test4"))
                .doOnSuccess({ response ->
                    getDataManager()
                        .updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_FB,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl()
                        )
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({ response ->
                    setIsLoading(false)
                    getNavigator()?.openMainActivity()
                }, { throwable ->
                    setIsLoading(false)
                    getNavigator()?.handleError(throwable)
                })
        )
    }

    fun onGoogleLoginClick() {
        setIsLoading(true)
        getCompositeDisposable().add(
            getDataManager()
                .doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest("test1", "test1"))
                .doOnSuccess({ response ->
                    getDataManager()
                        .updateUserInfo(
                            response.getAccessToken(),
                            response.getUserId(),
                            DataManager.LoggedInMode.LOGGED_IN_MODE_GOOGLE,
                            response.getUserName(),
                            response.getUserEmail(),
                            response.getGoogleProfilePicUrl()
                        )
                })
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({ response ->
                    setIsLoading(false)
                    getNavigator()?.openMainActivity()
                }, { throwable ->
                    setIsLoading(false)
                    getNavigator()?.handleError(throwable)
                })
        )
    }

    fun onServerLoginClick() {
        getNavigator()?.login()
    }
}
