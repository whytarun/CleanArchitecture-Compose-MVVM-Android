package com.android.mvvm.kotlin.ui.base

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.android.mvvm.kotlin.MvvmApp
import com.android.mvvm.kotlin.di.component.DaggerFragmentComponent
import com.android.mvvm.kotlin.di.component.FragmentComponent
import com.android.mvvm.kotlin.di.module.FragmentModule
import javax.inject.Inject

/**
 * Created by hemanth on 17,January,2021.
 */
abstract class BaseFragment<T : ViewDataBinding?, V : BaseViewModel<*>?>() :
@Inject
    Fragment(), Parcelable {
     var mActivity: BaseActivity<*,*>? = null
     var mRootView: View? = null
     var mViewDataBinding: T? = null

    //@Inject
     var mViewModel: V? = null

    constructor(parcel: Parcel) : this() {

    }


    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayoutId(): Int
    override fun onAttach(context: Context) {
        super.onAttach(requireContext())
        if (context is BaseActivity<*, *>) {
            val activity: BaseActivity<*,*> = context
            mActivity = activity
            activity.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(getBuildComponent())
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewDataBinding = inflate<T>(inflater, getLayoutId(), container, false)
        mRootView = mViewDataBinding!!.root
        return mRootView!!
    }

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.lifecycleOwner = this
        mViewDataBinding!!.executePendingBindings()
    }

    fun getBaseActivity(): BaseActivity<*,*>? {
        return mActivity
    }

    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    fun hideKeyboard() {
        if (mActivity != null) {
            mActivity!!.hideKeyboard()
        }
    }

    fun isNetworkConnected(): Boolean {
        return mActivity != null && mActivity!!.isNetworkConnected()
    }

    fun openActivityOnTokenExpire() {
        if (mActivity != null) {
            mActivity!!.openActivityOnTokenExpire()
        }
    }

    abstract fun performDependencyInjection(buildComponent: FragmentComponent?)
    private fun getBuildComponent(): FragmentComponent {
        return DaggerFragmentComponent.builder()
            .appComponent((requireContext().applicationContext as MvvmApp).appComponent)
            .fragmentModule(FragmentModule(this))
            .build()
    }

    interface Callback {
        fun onFragmentAttached()
        fun onFragmentDetached(tag: String?)
    }


}