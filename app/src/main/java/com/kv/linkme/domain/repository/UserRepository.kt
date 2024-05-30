package com.kv.linkme.domain.repository

import androidx.lifecycle.LiveData
import com.kv.linkme.core.database.UserDao
import com.kv.linkme.data.model.User


interface UserRepository {
    val allUsers: LiveData<List<User>>
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(user: User)
}
