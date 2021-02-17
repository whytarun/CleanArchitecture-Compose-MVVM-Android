package com.android.mvvm.kotlin.data.remote

import com.android.mvvm.kotlin.data.model.api.BlogResponse
import com.android.mvvm.kotlin.data.model.api.LoginRequest
import com.android.mvvm.kotlin.data.model.api.LoginResponse
import com.android.mvvm.kotlin.data.model.api.LogoutResponse
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by hemanth on 17,January,2021
 */
@Singleton
class AppApiHelper @Inject constructor(apiHeader: ApiHeader, retrofit: Retrofit) : ApiHelper {
     val mApiHeader: ApiHeader
     val mRetrofit: Retrofit
    override fun doFacebookLoginApiCall(request: LoginRequest.FacebookLoginRequest?): Single<LoginResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
            .addHeaders(mApiHeader.getPublicApiHeader())
            .addBodyParameter(request)
            .build()
            .getObjectSingle(LoginResponse::class.java)
    }

    override fun doGoogleLoginApiCall(request: LoginRequest.GoogleLoginRequest?): Single<LoginResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
            .addHeaders(mApiHeader.getPublicApiHeader())
            .addBodyParameter(request)
            .build()
            .getObjectSingle(LoginResponse::class.java)
    }

    override fun doLogoutApiCall(): Single<LogoutResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
            .addHeaders(mApiHeader.getProtectedApiHeader())
            .build()
            .getObjectSingle(LogoutResponse::class.java)
    }

    override fun doServerLoginApiCall(request: LoginRequest.ServerLoginRequest?): Single<LoginResponse> {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
            .addHeaders(mApiHeader.getPublicApiHeader())
            .addBodyParameter(request)
            .build()
            .getObjectSingle(LoginResponse::class.java)
    }

    override fun getApiHeader(): ApiHeader {
        return mApiHeader
    }

    override fun getBlogs(): Single<BlogResponse> {
        return mRetrofit.create(ApiHelper::class.java).blogs
    }

    init {
        mApiHeader = apiHeader
        mRetrofit = retrofit
    }
}
