package com.android.assessment.sciflare.technologies.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.assessment.sciflare.technologies.room.entity.User
import com.android.assessment.sciflare.technologies.room.dao.UserDao

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}