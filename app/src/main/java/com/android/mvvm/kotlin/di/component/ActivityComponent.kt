package com.android.mvvm.kotlin.di.component

import com.android.mvvm.kotlin.di.module.ActivityModule
import com.android.mvvm.kotlin.di.scope.ActivityScope
import com.android.mvvm.kotlin.ui.login.LoginActivity
import com.android.mvvm.kotlin.ui.main.MainActivity
import com.android.mvvm.kotlin.ui.splash.SplashActivity
import dagger.Component

/**
 * Created by hemanth on 17,January,2021
 */
@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [AppComponent::class])
open interface ActivityComponent {
    fun inject(activity: LoginActivity?)
    fun inject(activity: MainActivity?)
    fun inject(splashActivity: SplashActivity?)
}