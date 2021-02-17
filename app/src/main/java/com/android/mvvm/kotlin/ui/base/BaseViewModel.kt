package com.android.mvvm.kotlin.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

/**
 * Created by hemanth on 16,January,2021
 */
open class BaseViewModel<N>(
    dataManager: DataManager,
    schedulerProvider: SchedulerProvider
) : ViewModel() {
     val mDataManager: DataManager
     val mIsLoading = ObservableBoolean()
     val mSchedulerProvider: SchedulerProvider
     val mCompositeDisposable: CompositeDisposable
     var mNavigator: WeakReference<N>? = null
    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    fun getCompositeDisposable(): CompositeDisposable {
        return mCompositeDisposable
    }

    fun getDataManager(): DataManager {
        return mDataManager
    }

    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    fun getNavigator(): N? {
        return mNavigator!!.get()
    }

    fun setNavigator(navigator: N) {
        mNavigator = WeakReference(navigator)
    }

    fun getSchedulerProvider(): SchedulerProvider {
        return mSchedulerProvider
    }

    init {
        mDataManager = dataManager
        mSchedulerProvider = schedulerProvider
        mCompositeDisposable = CompositeDisposable()
    }
}
