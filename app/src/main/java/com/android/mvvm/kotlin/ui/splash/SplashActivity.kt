package com.android.mvvm.kotlin.ui.splash

import android.os.Bundle
import android.util.Log
import com.android.mvvm.kotlin.BR
import com.android.mvvm.kotlin.R
import com.android.mvvm.kotlin.databinding.ActivitySplashBinding
import com.android.mvvm.kotlin.di.component.ActivityComponent
import com.android.mvvm.kotlin.ui.base.BaseActivity
import com.android.mvvm.kotlin.ui.login.LoginActivity
import com.android.mvvm.kotlin.ui.main.MainActivity
import com.google.firebase.iid.FirebaseInstanceId
import dagger.Module
import javax.inject.Inject

/**
 * Created by hemanth on 17,January,2021
 */
@Module
 class SplashActivity @Inject constructor(): BaseActivity<ActivitySplashBinding?, SplashViewModel?>(), SplashNavigator {
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    //    private Handler mLeakyHandler = new Handler();
    override fun openLoginActivity() {
        val intent = LoginActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun openMainActivity() {
        val intent = MainActivity.newIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) { instanceIdResult ->
            val newToken = instanceIdResult.token
            if (newToken != null) {
                Log.d("newToken", newToken)
            }
        }

        mViewModel!!.setNavigator(this)
        mViewModel!!.startSeeding()

//        testMemoryLeak();

        fun performDependencyInjection(buildComponent: ActivityComponent) {
            buildComponent.inject(this)
        }
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

//        private void testMemoryLeak(){
//            mLeakyHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e("TAG", "run:Test data ");
//                }
//            }, 1000 * 10);
//        }
//        @Override
//        protected void onDestroy() {
//            super.onDestroy();
//            //This resolves the memory leak by removing the handler references.
//            mLeakyHandler.removeCallbacksAndMessages(null);
//        }

}
