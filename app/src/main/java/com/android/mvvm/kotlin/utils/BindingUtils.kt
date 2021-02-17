package com.android.mvvm.kotlin.utils

import androidx.databinding.BindingAdapter
import com.android.mvvm.kotlin.data.model.others.QuestionCardData
import com.android.mvvm.kotlin.ui.main.MainViewModel
import com.android.mvvm.kotlin.ui.main.QuestionCard
import com.mindorks.placeholderview.SwipePlaceHolderView

/**
 * Created by hemanth on 17,January,2021
 */
 object BindingUtils {
    @JvmStatic
    @BindingAdapter("adapter", "action")
    fun addQuestionItems(
            mCardsContainerView: SwipePlaceHolderView,
            mQuestionList: List<QuestionCardData?>?,
            mAction: Int
    ) {
        if (mAction == MainViewModel.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews()
                for (question in mQuestionList) {
                    if (question != null && question.options != null && question.options.size === 3) {
                        mCardsContainerView.addView<Any>(QuestionCard(question))
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView)
            }
        }
    }

//    @BindingAdapter("bind:imageUrl")
//    fun setImageUrl(imageView: ImageView, url: String?) {
//        val context = imageView.context
//        Glide.with(context).load(url).into(imageView)
//    }
}
