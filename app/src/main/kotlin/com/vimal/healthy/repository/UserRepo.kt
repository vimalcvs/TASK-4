package com.vimal.healthy.repository

import com.vimal.healthy.data.UserDao
import com.vimal.healthy.profilemodel.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val userDao: UserDao) {

    //insert user details to room
    suspend fun createUseRecords(user: User): Long {
        return userDao.insertToRoomDatabase(user)
    }

    // get single user details e.g with id 1
    val getUserDetails: Flow<List<User>> get() = userDao.getUserDetails()

    //delete single user record
    suspend fun deleteSingleUserRecord() {
        userDao.deleteSingleUserDetails(1)
    }

}