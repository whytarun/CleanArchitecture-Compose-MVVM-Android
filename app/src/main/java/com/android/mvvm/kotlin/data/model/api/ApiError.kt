package com.android.mvvm.kotlin.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by hemanth on 17,January,2021
 */
class ApiError(errorCode: Int, statusCode: String?, message: String?) {
    private val errorCode: Int

    @Expose
    @SerializedName("message")
    private val message: String?

    @Expose
    @SerializedName("status_code")
    private val statusCode: String?
    override fun equals(`object`: Any?): Boolean {
        if (this === `object`) {
            return true
        }
        if (`object` == null || javaClass != `object`.javaClass) {
            return false
        }
        val apiError = `object` as ApiError
        if (errorCode != apiError.errorCode) {
            return false
        }
        if (if (statusCode != null) statusCode != apiError.statusCode else apiError.statusCode != null) {
            return false
        }
        return if (message != null) message == apiError.message else apiError.message == null
    }

    override fun hashCode(): Int {
        var result = errorCode
        result = 31 * result + (statusCode?.hashCode() ?: 0)
        result = 31 * result + (message?.hashCode() ?: 0)
        return result
    }

    fun getErrorCode(): Int {
        return errorCode
    }

    fun getMessage(): String? {
        return message
    }

    fun getStatusCode(): String? {
        return statusCode
    }

    init {
        this.errorCode = errorCode
        this.statusCode = statusCode
        this.message = message
    }
}