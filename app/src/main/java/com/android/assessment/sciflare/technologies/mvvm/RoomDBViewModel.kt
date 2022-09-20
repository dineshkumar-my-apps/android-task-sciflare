package com.android.assessment.sciflare.technologies.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.assessment.sciflare.technologies.api.ApiHelper
import com.android.assessment.sciflare.technologies.room.DatabaseHelper
import com.android.assessment.sciflare.technologies.room.entity.User
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomDBViewModel(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModel() {

    val listUsers = MutableLiveData<List<User>>()
    val result = MutableLiveData<String>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        CoroutineScope(Dispatchers.IO).launch {
            val users = dbHelper.getUsers()
            println("-- output room size : ${users.size}")
            if (users.isEmpty()) {
                val response = apiHelper.getData()
                if (response.isSuccessful) {
                    val gson = Gson()
                    val result = gson.toJson(response.body())
                    println("-- retrofit output : $result")

                    val data = mutableListOf<User>()
                    for (apiUser in response.body()!!) {
                        val user = User(
                            apiUser._id!!,
                            apiUser.name!!,
                            apiUser.mobile!!,
                            apiUser.email!!,
                            apiUser.gender!!
                        )
                        data.add(user)
                    }

                    dbHelper.insertAll(data)
                    listUsers.postValue(data)
                } else {
                    result.postValue("No data")
                }
            } else {
                listUsers.postValue(users)
            }
        }
    }

    fun updateUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.updateUser(user)
            val users = dbHelper.getUsers()
            println("-- output room size : ${users.size}")
            if (users.isNotEmpty()) {
                listUsers.postValue(users)
            } else {
                result.postValue("No data")
            }
        }
    }

    fun deleteUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            dbHelper.deleteUser(user)
            val users = dbHelper.getUsers()
            println("-- output room size : ${users.size}")
            if (users.isNotEmpty()) {
                listUsers.postValue(users)
            } else {
                result.postValue("No data")
            }
        }
    }
}