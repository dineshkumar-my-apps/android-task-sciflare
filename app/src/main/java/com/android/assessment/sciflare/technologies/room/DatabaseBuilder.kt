package com.android.assessment.sciflare.technologies.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {

    private var INSTANCE: UserDatabase? = null

    fun getInstance(context: Context): UserDatabase {
        if (INSTANCE == null) {
            synchronized(UserDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "user-database"
        ).build()

}