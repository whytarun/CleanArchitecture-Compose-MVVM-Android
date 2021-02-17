package com.android.mvvm.kotlin.ui.splash

import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.ui.base.BaseViewModel
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider

/**
 * Created by hemanth on 17,January,2021
 */
class SplashViewModel(dataManager: DataManager?, schedulerProvider: SchedulerProvider?) :
    BaseViewModel<SplashNavigator?>(dataManager!!, schedulerProvider!!) {
    fun startSeeding() {
        getDataManager()
            .seedDatabaseQuestions()
            ?.flatMap({ aBoolean -> getDataManager().seedDatabaseOptions() })
            ?.subscribeOn(getSchedulerProvider().io())
            ?.observeOn(getSchedulerProvider().ui())
            ?.subscribe(
                { aBoolean -> decideNextActivity() },
                { throwable -> decideNextActivity() })?.let {
                getCompositeDisposable().add(
                    it
            )
            }
    }

    fun decideNextActivity() {
        if (getDataManager().getCurrentUserLoggedInMode() === DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type) {
            getNavigator()?.openLoginActivity()
        } else {
            getNavigator()?.openMainActivity()
        }
    }
}
