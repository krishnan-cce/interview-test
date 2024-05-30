package com.kv.linkme.data.repository

import androidx.lifecycle.LiveData
import com.kv.linkme.core.database.UserDao
import com.kv.linkme.data.model.User
import com.kv.linkme.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {
    override val allUsers: LiveData<List<User>> = userDao.getAllUsers()

    override suspend fun insert(user: User) {
        userDao.insert(user)
    }

    override suspend fun update(user: User) {
        userDao.update(user)
    }

    override suspend fun delete(user: User) {
        userDao.delete(user)
    }
}
