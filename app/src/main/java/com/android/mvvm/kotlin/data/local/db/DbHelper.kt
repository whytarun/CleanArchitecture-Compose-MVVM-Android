package com.android.mvvm.kotlin.data.local.db

import com.android.mvvm.kotlin.data.model.db.Option
import com.android.mvvm.kotlin.data.model.db.Question
import com.android.mvvm.kotlin.data.model.db.User
import io.reactivex.Observable

/**
 * Created by hemanth on 17,January,2021
 */
open interface DbHelper {
    fun getAllQuestions(): Observable<List<Question?>?>?
    fun getAllUsers(): Observable<List<User>>
    fun getOptionsForQuestionId(questionId: Long?): Observable<List<Option?>?>?
    fun insertUser(user: User?): Observable<Boolean?>?
    fun isOptionEmpty(): Observable<Boolean?>?
    fun isQuestionEmpty(): Observable<Boolean?>?
    fun saveOption(option: Option?): Observable<Boolean?>?
    fun saveOptionList(optionList: List<Option?>?): Observable<Boolean?>?
    fun saveQuestion(question: Question?): Observable<Boolean?>?
    fun saveQuestionList(questionList: List<Question?>?): Observable<Boolean?>?
}
