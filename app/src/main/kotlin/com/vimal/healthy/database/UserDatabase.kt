package com.vimal.healthy.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.vimal.healthy.data.UserDao
import com.vimal.healthy.global.ApplicationScope
import com.vimal.healthy.profilemodel.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [User::class], version = 2)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    class Callback @Inject constructor(
        private val songDatabase: Provider<UserDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            songDatabase.get().userDao()
            applicationScope.launch {

            }
        }
    }


}