package com.android.mvvm.kotlin.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.mvvm.kotlin.BR
import com.android.mvvm.kotlin.R
import com.android.mvvm.kotlin.databinding.ActivityLoginBinding
import com.android.mvvm.kotlin.di.component.ActivityComponent
import com.android.mvvm.kotlin.ui.base.BaseActivity
import com.android.mvvm.kotlin.ui.main.MainActivity
import dagger.Module
import javax.inject.Inject

/**
 * Created by hemanth on 16,January,2021
 */
@Module
  class LoginActivity @Inject constructor()
    :  BaseActivity<ActivityLoginBinding?, LoginViewModel?>(),
    LoginNavigator {
     var mActivityLoginBinding: ActivityLoginBinding? = null


    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun handleError(throwable: Throwable?) {
        // handle error
    }
    override fun login() {
        val email: String = mActivityLoginBinding?.etEmail?.getText().toString()
        val password: String = mActivityLoginBinding?.etPassword?.getText().toString()
        if (mViewModel?.isEmailAndPasswordValid(email, password)!!) {
            hideKeyboard()
            mViewModel?.login(email, password)
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun openMainActivity() {
        val intent: Intent = MainActivity.newIntent(this@LoginActivity)
        startActivity(intent)
        finish()
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityLoginBinding = getViewDataBinding()
        mViewModel?.setNavigator(this)

         Log.e("hermanth","hemanth")

    }
    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }


}
