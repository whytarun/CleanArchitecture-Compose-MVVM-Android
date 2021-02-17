package com.android.mvvm.kotlin.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by hemanth on 17,January,2021
 */
abstract class BaseViewHolder(itemView: View?) : RecyclerView.ViewHolder(
    itemView!!
) {
    abstract fun onBind(position: Int)
}