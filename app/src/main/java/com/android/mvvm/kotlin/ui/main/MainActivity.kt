package com.android.mvvm.kotlin.ui.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import com.android.mvvm.kotlin.BR
import com.android.mvvm.kotlin.R
import com.android.mvvm.kotlin.databinding.ActivityMainBinding
import com.android.mvvm.kotlin.databinding.NavHeaderMainBinding
import com.android.mvvm.kotlin.di.component.ActivityComponent
import com.android.mvvm.kotlin.ui.base.BaseActivity
import com.android.mvvm.kotlin.ui.location.LocationFragment
import com.android.mvvm.kotlin.ui.login.LoginActivity
import com.android.mvvm.kotlin.utils.ScreenUtils
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.mindorks.placeholderview.BuildConfig
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView
import com.opensooq.supernova.gligar.GligarPicker
import dagger.Module
import javax.inject.Inject

/**
 * Created by hemanth on 17,January,2021
 */

@Module
class MainActivity @Inject constructor() : BaseActivity<ActivityMainBinding?, MainViewModel?>(),
    MainNavigator {
    var mActivityMainBinding: ActivityMainBinding? = null
    var mCardsContainerView: SwipePlaceHolderView? = null
    var mDrawer: DrawerLayout? = null
    var mNavigationView: NavigationView? = null
    var mToolbar: Toolbar? = null
    val PICKER_REQUEST_CODE = 100
    lateinit var navHeaderMainBinding: NavHeaderMainBinding
    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun onBackPressed() {
        val fragmentManager: FragmentManager = getSupportFragmentManager()
        val fragment = fragmentManager.findFragmentByTag("LocationFragment")
        if (fragment == null) {
            super.onBackPressed()
        } else {
            onFragmentDetached("LocationFragment")
        }
    }

    override fun onFragmentDetached(tag: String?) {
        val fragmentManager: FragmentManager = getSupportFragmentManager()
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(fragment)
                .commitNow()
            unlockDrawer()
        }
    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = getViewDataBinding()
        mViewModel?.setNavigator(this)
        setUp()
    }

    protected fun onResæume() {
        super.onResume()
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    fun lockDrawer() {
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    private fun setUp() {
        mDrawer = mActivityMainBinding?.drawerView
        mToolbar = mActivityMainBinding?.toolbar
        mNavigationView = mActivityMainBinding?.navigationView
        mCardsContainerView = mActivityMainBinding?.cardsContainer
        setSupportActionBar(mToolbar)
        val mDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            mDrawer,
            mToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideKeyboard()
            }
        }
        mDrawer!!.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        setupNavMenu()
        val version: String =
            getString(R.string.version).toString() + " " + BuildConfig.VERSION_NAME
        mViewModel?.updateAppVersion(version)
        mViewModel?.onNavMenuCreated()

        subscribeToLiveData()
        setupCardContainerView()
    }

    private fun setupCardContainerView() {
        val screenWidth: Int = ScreenUtils.getScreenWidth(this)
        val screenHeight: Int = ScreenUtils.getScreenHeight(this)
        mCardsContainerView!!.builder
            .setDisplayViewCount(3)
            .setHeightSwipeDistFactor(10f)
            .setWidthSwipeDistFactor(5f)
            .setSwipeDecor(
                SwipeDecor()
                    .setViewWidth((0.90 * screenWidth).toInt())
                    .setViewHeight((0.75 * screenHeight).toInt())
                    .setPaddingTop(20)
                    .setSwipeRotationAngle(10)
                    .setRelativeScale(0.01f)
            )
        mCardsContainerView!!.addItemRemoveListener { count: Int ->
            if (count == 0) {
                // reload the contents again after 1 sec delay
                Handler(getMainLooper()).postDelayed(Runnable {
                    //Reload once all the cards are removed
                    mViewModel?.loadQuestionCards()
                }, 800)
            } else {
                mViewModel?.removeQuestionCard()
            }
        }
    }

    private fun setupNavMenu() {
        navHeaderMainBinding = DataBindingUtil.inflate(
            getLayoutInflater(),
            R.layout.nav_header_main, mActivityMainBinding?.navigationView, false
        )
        mActivityMainBinding?.navigationView?.addHeaderView(navHeaderMainBinding.root)
        navHeaderMainBinding.setViewModel(mViewModel)
        navHeaderMainBinding.ivProfilePic.setOnClickListener({ view ->
            GligarPicker().requestCode(
                PICKER_REQUEST_CODE
            ).withActivity(this).show()
        })
        mNavigationView!!.setNavigationItemSelectedListener { item: MenuItem ->
            mDrawer!!.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.navItemLocation -> {
                    showLocationFragment()
                    return@setNavigationItemSelectedListener true
                }
                R.id.navItemLogout -> {
                    mViewModel?.logout()
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    private fun showLocationFragment() {
        lockDrawer()
        getSupportFragmentManager()
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(R.id.clRootView, LocationFragment.newInstance(), LocationFragment.TAG)
            .commit()
    }

    private fun subscribeToLiveData() {
        mViewModel?.getQuestionCardData()?.observe(
            this,
            { questionCardDatas -> mViewModel!!.setQuestionDataList(questionCardDatas) })
    }

    private fun unlockDrawer() {
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                val pathsList =
                    data!!.extras!!.getStringArray(GligarPicker.IMAGES_RESULT) // return list of selected images paths.
                if (pathsList != null && pathsList.size > 0) mViewModel?.setUserProfilePicUrl(
                    pathsList[0]
                )
                Glide.with(this).load(pathsList!![0]).into(navHeaderMainBinding!!.ivProfilePic)
            }
        }
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }

        @BindingAdapter("imgUrl")
        fun setProfilePicture(imageView: ImageView, imgUrl: String?) {
            Glide.with(imageView.context).load(imgUrl).into(imageView)
        }
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent?) {
        buildComponent?.inject(this)
    }

}
