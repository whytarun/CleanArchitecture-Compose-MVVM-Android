package com.android.mvvm.kotlin.data.remote;

import com.android.mvvm.kotlin.data.model.api.BlogResponse;
import com.android.mvvm.kotlin.data.model.api.LoginRequest;
import com.android.mvvm.kotlin.data.model.api.LoginResponse;
import com.android.mvvm.kotlin.data.model.api.LogoutResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by hemanth on 17,January,2021
 */
public interface ApiHelper {

    Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request);

    Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request);

    Single<LogoutResponse> doLogoutApiCall();

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);

    ApiHeader getApiHeader();

    @GET("5926ce9d11000096006ccb30")
    Single<BlogResponse> getBlogs();

}