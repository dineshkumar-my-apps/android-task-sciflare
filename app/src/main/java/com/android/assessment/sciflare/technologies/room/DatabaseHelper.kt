package com.android.assessment.sciflare.technologies.room

import com.android.assessment.sciflare.technologies.room.entity.User

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun updateUser(user : User)

    suspend fun deleteUser(user : User)

    suspend fun insertAll(users: List<User>)

}