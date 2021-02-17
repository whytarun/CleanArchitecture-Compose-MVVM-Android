package com.android.mvvm.kotlin.utils

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import androidx.core.util.PatternsCompat
import com.android.mvvm.kotlin.R
import java.io.IOException
import java.io.InputStream
import java.nio.channels.AsynchronousSocketChannel.open
import java.nio.channels.Pipe.open

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by hemanth on 17,January,2021
 */
object CommonUtils {
    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun getTimestamp(): String {
        return SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.US).format(Date())
    }

    fun isEmailValid(email: String?): Boolean {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String?): String {
        val manager = context.assets
        val `is` = manager.open(jsonFileName!!)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, "UTF-8")
    }

    fun readJSONFromAsset(context: Context,jsonFileName: String?): String? {
        var json: String? = null
        try {
            val  inputStream: InputStream = context.assets.open(jsonFileName.toString())
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }


    private fun String(bytes: ByteArray, charset: String): String {
        TODO("Not yet implemented")
    }

    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }
}
