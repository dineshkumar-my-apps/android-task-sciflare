package com.android.assessment.sciflare.technologies.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.assessment.sciflare.technologies.room.entity.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : User)

    @Insert
    suspend fun insertAll(users :List<User>)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("Select * from user")
    fun getAllUsers(): List<User>

}