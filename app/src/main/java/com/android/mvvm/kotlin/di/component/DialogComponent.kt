package com.android.mvvm.kotlin.di.component

import com.android.mvvm.kotlin.di.module.DialogModule
import com.android.mvvm.kotlin.di.scope.DialogScope
import com.android.mvvm.kotlin.ui.base.BaseDialog
import dagger.Component

/**
 * Created by hemanth on 17,January,2021
 */
@DialogScope
@Component(modules = [DialogModule::class], dependencies = [AppComponent::class])
open interface DialogComponent {
    fun inject(baseDialog: BaseDialog?)
}
