package com.android.mvvm.kotlin.di

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier

/**
 * Created by hemanth on 17,January,2021
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
annotation class PreferenceInfo
