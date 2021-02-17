package com.android.mvvm.kotlin.data.remote

import com.android.mvvm.kotlin.BuildConfig


/**
 * Created by hemanth on 17,January,2021
 */
object ApiEndPoint {
    const val ENDPOINT_FACEBOOK_LOGIN: String =
        BuildConfig.BASE_URL + "588d15d3100000ae072d2944"
    const val ENDPOINT_GOOGLE_LOGIN: String = BuildConfig.BASE_URL + "588d14f4100000a9072d2943"
    const val ENDPOINT_LOGOUT: String = BuildConfig.BASE_URL + "588d161c100000a9072d2946"
    const val ENDPOINT_SERVER_LOGIN: String = BuildConfig.BASE_URL + "588d15f5100000a8072d2945"
}
