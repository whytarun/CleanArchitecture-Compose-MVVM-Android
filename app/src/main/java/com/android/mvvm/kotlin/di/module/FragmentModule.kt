package com.android.mvvm.kotlin.di.module

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mvvm.kotlin.ViewModelProviderFactory
import com.android.mvvm.kotlin.data.DataManager
import com.android.mvvm.kotlin.ui.base.BaseFragment
import com.android.mvvm.kotlin.ui.location.LocationViewModel
import com.android.mvvm.kotlin.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by hemanth on 17,January,2021
 */
@Module
class FragmentModule(fragment: BaseFragment<*, *>) {
     val fragment: BaseFragment<*, *>
    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(fragment.getActivity())
    }

    @Provides
    fun provideLocationViewModel(
        dataManager: DataManager?,
        schedulerProvider: SchedulerProvider?
    ): LocationViewModel {
        val supplier: Supplier<LocationViewModel> =
            Supplier<LocationViewModel> {
                LocationViewModel(
                    dataManager,
                    schedulerProvider
                )
            }
        val factory: ViewModelProviderFactory<LocationViewModel> = ViewModelProviderFactory(
            LocationViewModel::class.java, supplier
        )
        return ViewModelProvider(fragment, factory).get(LocationViewModel::class.java)
    }

    init {
        this.fragment = fragment
    }
}