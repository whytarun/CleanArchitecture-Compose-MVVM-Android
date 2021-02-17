package com.android.mvvm.kotlin

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import javax.inject.Singleton

/**
 * Created by hemanth on 17,January,2021
 */
@Singleton
class ViewModelProviderFactory<T : ViewModel?>(
        viewModelClass: Class<T>,
        viewModelSupplier: Supplier<T>
) :
        NewInstanceFactory() {
    val viewModelClass: Class<T>
    val viewModelSupplier: Supplier<T>
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(viewModelClass)) {
            viewModelSupplier.get() as T
        } else {
            throw IllegalArgumentException("Unknown Class name " + viewModelClass.name)
        }
    }

    init {
        this.viewModelClass = viewModelClass
        this.viewModelSupplier = viewModelSupplier
    }
}