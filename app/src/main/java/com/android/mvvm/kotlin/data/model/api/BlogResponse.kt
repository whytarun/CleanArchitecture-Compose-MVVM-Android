package com.android.mvvm.kotlin.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by hemanth on 17,January,2021
 */
class BlogResponse {
    @Expose
    @SerializedName("data")
    private val data: List<Blog>? = null

    @Expose
    @SerializedName("message")
    private val message: String? = null

    @Expose
    @SerializedName("status_code")
    private val statusCode: String? = null
      fun equals(o: Any): Any {
        if (this === o) {
            return true
        }
        if (o !is BlogResponse) {
            return false
        }
        val that = o
        if (statusCode != that.statusCode) {
            return false
        }
        return if (message != that.message) {
            false
        } else data == that.data
    }

    override fun hashCode(): Int {
        var result = statusCode.hashCode()
        result = 31 * result + message.hashCode()
        result = 31 * result + data.hashCode()
        return result
    }

    fun getData(): List<Blog>? {
        return data
    }

    fun getMessage(): String? {
        return message
    }

    fun getStatusCode(): String? {
        return statusCode
    }

    class Blog(
        @field:SerializedName("author") @field:Expose val author: String,
        @field:SerializedName(
            "blog_url"
        ) @field:Expose val blogUrl: String,
        @field:SerializedName("img_url") @field:Expose val coverImgUrl: String,
        @field:SerializedName(
            "published_at"
        ) @field:Expose val date: String,
        @field:SerializedName("description") @field:Expose val description: String,
        @field:SerializedName(
            "title"
        ) @field:Expose val title: String
    ) {

        override fun equals(o: Any?): Boolean {
            if (this === o) {
                return true
            }
            if (o !is Blog) {
                return false
            }
            val blog = o
            if (blogUrl != blog.blogUrl) {
                return false
            }
            if (coverImgUrl != blog.coverImgUrl) {
                return false
            }
            if (title != blog.title) {
                return false
            }
            if (description != blog.description) {
                return false
            }
            return if (author != blog.author) {
                false
            } else date == blog.date
        }

        override fun hashCode(): Int {
            var result = blogUrl.hashCode()
            result = 31 * result + coverImgUrl.hashCode()
            result = 31 * result + title.hashCode()
            result = 31 * result + description.hashCode()
            result = 31 * result + author.hashCode()
            result = 31 * result + date.hashCode()
            return result
        }

    }
}