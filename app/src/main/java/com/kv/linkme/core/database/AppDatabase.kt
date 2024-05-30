package com.kv.linkme.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kv.linkme.data.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
