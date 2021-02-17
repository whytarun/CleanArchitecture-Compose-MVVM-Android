package com.android.mvvm.kotlin.di.component

import com.android.mvvm.kotlin.di.module.FragmentModule
import com.android.mvvm.kotlin.di.scope.FragmentScope
import com.android.mvvm.kotlin.ui.location.LocationFragment
import dagger.Component

/**
 * Created by hemanth on 17,January,2021
 */
@FragmentScope
@Component(modules = [FragmentModule::class], dependencies = [AppComponent::class])
open interface FragmentComponent {
    fun inject(locationFragment: LocationFragment?)

}


