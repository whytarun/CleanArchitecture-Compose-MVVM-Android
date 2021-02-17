package com.android.mvvm.kotlin.data.remote

import com.android.mvvm.kotlin.di.ApiInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by hemanth on 17,January,2021
 */
@Singleton
class ApiHeader @Inject constructor(
    publicApiHeader: PublicApiHeader,
    protectedApiHeader: ProtectedApiHeader
) {
    private val mProtectedApiHeader: ProtectedApiHeader = protectedApiHeader
    private val mPublicApiHeader: PublicApiHeader = publicApiHeader
    fun getProtectedApiHeader(): ProtectedApiHeader {
        return mProtectedApiHeader
    }

    fun getPublicApiHeader(): PublicApiHeader {
        return mPublicApiHeader
    }

    class ProtectedApiHeader constructor(apiKey: String, userId: Long, accessToken: String) {
        @field:SerializedName("api_key")
        @field:Expose
        var apiKey: String = apiKey

        @field:SerializedName("user_id")
        @field:Expose
        var userId: Long = userId

        @field:SerializedName("access_token")
        @field:Expose
        var accessToken: String = accessToken
    }

    class PublicApiHeader @Inject constructor(@field:SerializedName("api_key") @field:Expose @param:ApiInfo var apiKey: String)

}