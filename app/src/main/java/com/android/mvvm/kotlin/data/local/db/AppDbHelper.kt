package com.android.mvvm.kotlin.data.local.db

import com.android.mvvm.kotlin.data.model.db.Option
import com.android.mvvm.kotlin.data.model.db.Question
import com.android.mvvm.kotlin.data.model.db.User
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by hemanth on 17,January,2021
 */
@Singleton
open class AppDbHelper @Inject constructor(appDatabase: AppDatabase) : DbHelper {
     val mAppDatabase: AppDatabase
    override fun getAllQuestions(): Observable<List<Question?>?>? {
        return mAppDatabase.questionDao()?.loadAll()
            ?.toObservable()
    }

    override fun getAllUsers(): Observable<List<User>> {
        return Observable.fromCallable<List<User>> {
            mAppDatabase.userDao()?.loadAll() as List<User>?
        }
    }

    override fun getOptionsForQuestionId(questionId: Long?): Observable<List<Option?>?>? {
        return mAppDatabase.optionDao()?.loadAllByQuestionId(questionId)
            ?.toObservable()
    }

    override fun insertUser(user: User?): Observable<Boolean?>? {
        return Observable.fromCallable {
            mAppDatabase.userDao()?.insert(user)
            true
        }
    }

    override fun isOptionEmpty(): Observable<Boolean?>? {
        return mAppDatabase.optionDao()?.loadAll()
            ?.flatMapObservable({ options -> Observable.just(options.isEmpty()) })
    }

    override fun isQuestionEmpty(): Observable<Boolean?>? {
        return mAppDatabase.questionDao()?.loadAll()
            ?.flatMapObservable({ questions -> Observable.just(questions.isEmpty()) })
    }

    override fun saveOption(option: Option?): Observable<Boolean?>? {
        return Observable.fromCallable {
            mAppDatabase.optionDao()?.insert(option)
            true
        }
    }

    override fun saveOptionList(optionList: List<Option?>?): Observable<Boolean?>? {
        return Observable.fromCallable {
            mAppDatabase.optionDao()?.insertAll(optionList)
            true
        }
    }

    override fun saveQuestion(question: Question?): Observable<Boolean?>? {
        return Observable.fromCallable {
            mAppDatabase.questionDao()?.insert(question)
            true
        }
    }

    override fun saveQuestionList(questionList: List<Question?>?): Observable<Boolean?>? {
        return Observable.fromCallable {
            mAppDatabase.questionDao()?.insertAll(questionList)
            true
        }
    }

    init {
        mAppDatabase = appDatabase
    }
}