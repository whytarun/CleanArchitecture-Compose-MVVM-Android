package com.android.mvvm.kotlin.data.model.others

import com.android.mvvm.kotlin.data.model.db.Option
import com.android.mvvm.kotlin.data.model.db.Question

/**
 * Created by hemanth on 17,January,2021
 */
class QuestionCardData(question: Question, options: List<Option>) {
    var mShowCorrectOptions = false
    var options: List<Option>
    var question: Question

    init {
        this.question = question
        this.options = options
    }
}
