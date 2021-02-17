package com.android.mvvm.kotlin.ui.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.android.mvvm.kotlin.MvvmApp
import com.android.mvvm.kotlin.di.component.ActivityComponent
import com.android.mvvm.kotlin.di.component.DaggerActivityComponent
import com.android.mvvm.kotlin.di.module.ActivityModule
import com.android.mvvm.kotlin.ui.login.LoginActivity
import com.android.mvvm.kotlin.utils.CommonUtils
import com.android.mvvm.kotlin.utils.NetworkUtils
import javax.inject.Inject

/**
 * Created by hemanth on 16,January,2021
 */
open abstract class BaseActivity<T : ViewDataBinding?, V : BaseViewModel<*>?> :

        AppCompatActivity(), BaseFragment.Callback {
    // TODO
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    var mProgressDialog: ProgressDialog? = null
    var mViewDataBinding: T? = null

    @set:Inject
    var mViewModel: V? = null

    // lateinit var loginActivity: LoginActivity


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
    override fun onFragmentAttached() {}
    override fun onFragmentDetached(tag: String?) {}
    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection(getBuildComponent())
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    fun getViewDataBinding(): T? {
        return mViewDataBinding
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String?): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }


    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.cancel()
        }
    }

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    fun openActivityOnTokenExpire() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    private fun getBuildComponent(): ActivityComponent {

        return DaggerActivityComponent.builder()
                .appComponent((application as MvvmApp).appComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    abstract fun performDependencyInjection(buildComponent: ActivityComponent?)

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions!!, requestCode)
        }
    }

    fun showLoading() {
        hideLoading()
        mProgressDialog = CommonUtils.showLoadingDialog(this)
    }

    open fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView<T>(this, getLayoutId())
        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.executePendingBindings()
    }
}
