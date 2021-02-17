package com.android.mvvm.kotlin.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by hemanth on 17,January,2021
 */
@Entity(tableName = "users")
class User {
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null

    @PrimaryKey
    var id: Long? = null
    var name: String? = null

    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null
}
