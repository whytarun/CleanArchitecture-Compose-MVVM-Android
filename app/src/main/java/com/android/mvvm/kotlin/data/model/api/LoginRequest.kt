package com.android.mvvm.kotlin.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by hemanth on 17,January,2021
 */
class LoginRequest private constructor() {
    class FacebookLoginRequest(
        @field:SerializedName("fb_user_id") @field:Expose val fbUserId: String?,
        @field:SerializedName(
            "fb_access_token"
        ) @field:Expose val fbAccessToken: String?
    ) {

        override fun equals(`object`: Any?): Boolean {
            if (this === `object`) {
                return true
            }
            if (`object` == null || javaClass != `object`.javaClass) {
                return false
            }
            val that = `object` as FacebookLoginRequest
            if (if (fbUserId != null) fbUserId != that.fbUserId else that.fbUserId != null) {
                return false
            }
            return if (fbAccessToken != null) fbAccessToken == that.fbAccessToken else that.fbAccessToken == null
        }

        override fun hashCode(): Int {
            var result = fbUserId?.hashCode() ?: 0
            result = 31 * result + (fbAccessToken?.hashCode() ?: 0)
            return result
        }

    }

    class GoogleLoginRequest(
        @field:SerializedName("google_user_id") @field:Expose val googleUserId: String?,
        @field:SerializedName(
            "google_id_token"
        ) @field:Expose val idToken: String?
    ) {

        override fun equals(`object`: Any?): Boolean {
            if (this === `object`) {
                return true
            }
            if (`object` == null || javaClass != `object`.javaClass) {
                return false
            }
            val that = `object` as GoogleLoginRequest
            if (if (googleUserId != null) googleUserId != that.googleUserId else that.googleUserId != null) {
                return false
            }
            return if (idToken != null) idToken == that.idToken else that.idToken == null
        }

        override fun hashCode(): Int {
            var result = googleUserId?.hashCode() ?: 0
            result = 31 * result + (idToken?.hashCode() ?: 0)
            return result
        }

    }

    class ServerLoginRequest(
        @field:SerializedName("email") @field:Expose val email: String?, @field:SerializedName(
            "password"
        ) @field:Expose val password: String?
    ) {

        override fun equals(`object`: Any?): Boolean {
            if (this === `object`) {
                return true
            }
            if (`object` == null || javaClass != `object`.javaClass) {
                return false
            }
            val that = `object` as ServerLoginRequest
            if (if (email != null) email != that.email else that.email != null) {
                return false
            }
            return if (password != null) password == that.password else that.password == null
        }

        override fun hashCode(): Int {
            var result = email?.hashCode() ?: 0
            result = 31 * result + (password?.hashCode() ?: 0)
            return result
        }

    }
}