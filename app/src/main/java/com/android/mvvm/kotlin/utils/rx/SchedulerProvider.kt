package com.android.mvvm.kotlin.utils.rx

import io.reactivex.Scheduler

/**
 * Created by hemanth on 17,January,2021
 */
open interface SchedulerProvider {
    fun computation(): Scheduler?
    fun io(): Scheduler?
    fun ui(): Scheduler?
}
