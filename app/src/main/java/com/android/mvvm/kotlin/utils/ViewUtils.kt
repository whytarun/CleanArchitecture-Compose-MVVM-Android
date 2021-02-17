package com.android.mvvm.kotlin.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import androidx.core.content.ContextCompat
import com.android.mvvm.kotlin.R

/**
 * Created by hemanth on 17,January,2021
 */
object ViewUtils {
    fun changeIconDrawableToGray(context: Context?, drawable: Drawable?) {
        if (drawable != null) {
            drawable.mutate()
            drawable.setColorFilter(
                ContextCompat.getColor(context!!, R.color.dark_gray),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return Math.round(dp * density)
    }

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }

    fun dpToPx(dp: Float, metrics: DisplayMetrics): Int {
        val density = metrics.density
        return Math.round(dp * density)
    }

    fun pxToDp(px: Float, metrics: DisplayMetrics): Float {
        val densityDpi = metrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }
}
