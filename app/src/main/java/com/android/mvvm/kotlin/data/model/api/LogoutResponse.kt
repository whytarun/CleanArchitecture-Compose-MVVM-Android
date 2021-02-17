package com.android.mvvm.kotlin.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by hemanth on 17,January,2021
 */
class LogoutResponse {
    @Expose
    @SerializedName("message")
    private var message: String? = null

    @Expose
    @SerializedName("status_code")
    private var statusCode: String? = null
    override fun equals(`object`: Any?): Boolean {
        if (this === `object`) {
            return true
        }
        if (`object` == null || javaClass != `object`.javaClass) {
            return false
        }
        val that = `object` as LogoutResponse
        if (if (statusCode != null) statusCode != that.statusCode else that.statusCode != null) {
            return false
        }
        return if (message != null) message == that.message else that.message == null
    }

    override fun hashCode(): Int {
        var result = if (statusCode != null) statusCode.hashCode() else 0
        result = 31 * result + if (message != null) message.hashCode() else 0
        return result
    }

    fun getMessage(): String? {
        return message
    }

    fun getStatusCode(): String? {
        return statusCode
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun setStatusCode(statusCode: String?) {
        this.statusCode = statusCode
    }
}
