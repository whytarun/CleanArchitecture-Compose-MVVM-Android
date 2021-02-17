package com.android.mvvm.kotlin.di.component

import android.app.Application
import com.android.mvvm.kotlin.MvvmApp
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.di.module.AppModule
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by hemanth on 17,January,2021
 */
@Singleton
@Component(modules = [AppModule::class])
open interface AppComponent {
    fun inject(app: MvvmApp?)
    fun getDataManager(): DataManager?
    fun getSchedulerProvider(): SchedulerProvider?

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder?
        fun build(): AppComponent?
    }
}