package com.vimal.healthy.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.vimal.healthy.profilemodel.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToRoomDatabase(user: User): Long

    @Transaction
    @Query("SELECT * FROM users_table ORDER BY id DESC")
    fun getUserDetails(): Flow<List<User>>

    // update user details
    @Update
    suspend fun updateUserDetails(user: User)

    /**
     * DELETE
     */

    //delete single user details
    @Query("DELETE FROM users_table WHERE id = :id")
    suspend fun deleteSingleUserDetails(id: Int)

    // delete all user details
    @Delete
    suspend fun deleteAllUsersDetails(user: User)

}