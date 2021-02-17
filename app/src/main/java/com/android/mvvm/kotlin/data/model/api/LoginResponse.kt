package com.android.mvvm.kotlin.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by hemanth on 17,January,2021
 */
class LoginResponse {
    @Expose
    @SerializedName("access_token")
    private val accessToken: String? = null

    @Expose
    @SerializedName("fb_profile_pic_url")
    private val fbProfilePicUrl: String? = null

    @Expose
    @SerializedName("google_profile_pic_url")
    private val googleProfilePicUrl: String? = null

    @Expose
    @SerializedName("message")
    private val message: String? = null

    @Expose
    @SerializedName("server_profile_pic_url")
    private val serverProfilePicUrl: String? = null

    @Expose
    @SerializedName("status_code")
    private val statusCode: String? = null

    @Expose
    @SerializedName("email")
    private val userEmail: String? = null

    @Expose
    @SerializedName("user_id")
    private val userId: Long? = null

    @Expose
    @SerializedName("user_name")
    private val userName: String? = null
    override fun equals(`object`: Any?): Boolean {
        if (this === `object`) {
            return true
        }
        if (`object` == null || javaClass != `object`.javaClass) {
            return false
        }
        val that = `object` as LoginResponse
        if (if (statusCode != null) statusCode != that.statusCode else that.statusCode != null) {
            return false
        }
        if (if (userId != null) userId != that.userId else that.userId != null) {
            return false
        }
        if (if (accessToken != null) accessToken != that.accessToken else that.accessToken != null) {
            return false
        }
        if (if (userName != null) userName != that.userName else that.userName != null) {
            return false
        }
        if (if (userEmail != null) userEmail != that.userEmail else that.userEmail != null) {
            return false
        }
        if (if (serverProfilePicUrl != null) serverProfilePicUrl != that.serverProfilePicUrl else that.serverProfilePicUrl != null) {
            return false
        }
        if (if (fbProfilePicUrl != null) fbProfilePicUrl != that.fbProfilePicUrl else that.fbProfilePicUrl != null) {
            return false
        }
        if (if (googleProfilePicUrl != null) googleProfilePicUrl != that.googleProfilePicUrl else that.googleProfilePicUrl != null) {
            return false
        }
        return if (message != null) message == that.message else that.message == null
    }

    override fun hashCode(): Int {
        var result = statusCode?.hashCode() ?: 0
        result = 31 * result + (userId?.hashCode() ?: 0)
        result = 31 * result + (accessToken?.hashCode() ?: 0)
        result = 31 * result + (userName?.hashCode() ?: 0)
        result = 31 * result + (userEmail?.hashCode() ?: 0)
        result = 31 * result + (serverProfilePicUrl?.hashCode() ?: 0)
        result = 31 * result + (fbProfilePicUrl?.hashCode() ?: 0)
        result = 31 * result + (googleProfilePicUrl?.hashCode() ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }

    fun getAccessToken(): String? {
        return accessToken
    }

    fun getFbProfilePicUrl(): String? {
        return fbProfilePicUrl
    }

    fun getGoogleProfilePicUrl(): String? {
        return googleProfilePicUrl
    }

    fun getMessage(): String? {
        return message
    }

    fun getServerProfilePicUrl(): String? {
        return serverProfilePicUrl
    }

    fun getStatusCode(): String? {
        return statusCode
    }

    fun getUserEmail(): String? {
        return userEmail
    }

    fun getUserId(): Long? {
        return userId
    }

    fun getUserName(): String? {
        return userName
    }
}
