package com.android.assessment.sciflare.technologies.room

import com.android.assessment.sciflare.technologies.room.entity.User

class DatabaseHelperImpl(private val appDatabase: UserDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.getUserDao().getAllUsers()

    override suspend fun updateUser(user : User) = appDatabase.getUserDao().update(user)

    override suspend fun deleteUser(user : User) = appDatabase.getUserDao().delete(user)

    override suspend fun insertAll(users: List<User>) = appDatabase.getUserDao().insertAll(users)

}