package com.android.mvvm.kotlin.di.module

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.android.mvvm.kotlin.ViewModelProviderFactory
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.ui.base.BaseActivity
import com.android.mvvm.kotlin.ui.login.LoginViewModel
import com.android.mvvm.kotlin.ui.main.MainViewModel
import com.android.mvvm.kotlin.ui.splash.SplashViewModel
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by hemanth on 17,January,2021
 */
@Module
 class ActivityModule(private val activity: BaseActivity<*, *>) {

    @Provides
    fun provideMainViewModel(
        dataManager: DataManager?,
        schedulerProvider: SchedulerProvider?
    ): MainViewModel {
        val supplier: Supplier<MainViewModel> =
            Supplier<MainViewModel> {
                MainViewModel(
                    dataManager,
                    schedulerProvider
                )
            }
        val factory: ViewModelProviderFactory<MainViewModel> = ViewModelProviderFactory(
            MainViewModel::class.java, supplier
        )
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    @Provides
    fun provideLoginViewModel(
        dataManager: DataManager?,
        schedulerProvider: SchedulerProvider?
    ): LoginViewModel {
        val supplier: Supplier<LoginViewModel> =
            Supplier<LoginViewModel> {
                LoginViewModel(
                    dataManager,
                    schedulerProvider
                )
            }
        val factory: ViewModelProviderFactory<LoginViewModel> = ViewModelProviderFactory(
            LoginViewModel::class.java, supplier
        )
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }

    @Provides
    fun provideSplashViewModel(
        dataManager: DataManager?,
        schedulerProvider: SchedulerProvider?
    ): SplashViewModel {
        val supplier: Supplier<SplashViewModel> =
            Supplier<SplashViewModel> {
                SplashViewModel(
                    dataManager,
                    schedulerProvider
                )
            }
        val factory: ViewModelProviderFactory<SplashViewModel> = ViewModelProviderFactory(
            SplashViewModel::class.java, supplier
        )
        return ViewModelProvider(activity, factory).get(SplashViewModel::class.java)
    }

}
