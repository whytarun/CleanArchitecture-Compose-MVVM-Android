package com.android.mvvm.kotlin.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.mvvm.kotlin.data.local.db.dao.OptionDao
import com.android.mvvm.kotlin.data.local.db.dao.QuestionDao
import com.android.mvvm.kotlin.data.local.db.dao.UserDao
import com.android.mvvm.kotlin.data.model.db.Option
import com.android.mvvm.kotlin.data.model.db.Question
import com.android.mvvm.kotlin.data.model.db.User

/**
 * Created by hemanth on 17,January,2021
 */
@Database(entities = [User::class, Question::class, Option::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun optionDao(): OptionDao?
    abstract fun questionDao(): QuestionDao?
    abstract fun userDao(): UserDao?
}