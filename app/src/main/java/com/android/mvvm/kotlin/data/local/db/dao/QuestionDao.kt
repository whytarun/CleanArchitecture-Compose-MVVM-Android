package com.android.mvvm.kotlin.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.mvvm.kotlin.data.model.db.Question
import io.reactivex.Single

/**
 * Created by hemanth on 17,January,2021
 */
@Dao
open interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(question: Question?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(questions: List<Question?>?)

    @Query("SELECT * FROM questions")
    fun loadAll(): Single<List<Question?>?>?
}
