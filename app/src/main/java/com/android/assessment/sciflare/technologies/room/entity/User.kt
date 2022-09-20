package com.android.assessment.sciflare.technologies.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val _id: String = "",
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "mobile")
    val mobile: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "gender")
    val gender: String
)