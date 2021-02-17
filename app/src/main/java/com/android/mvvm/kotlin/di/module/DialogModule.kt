package com.android.mvvm.kotlin.di.module

import com.android.mvvm.kotlin.ui.base.BaseDialog
import dagger.Module

/**
 * Created by hemanth on 17,January,2021
 */
@Module
class DialogModule(dialog: BaseDialog) {
     val dialog: BaseDialog

    init {
        this.dialog = dialog
    }
}
