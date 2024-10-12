package com.tanveer.airlib.task.shared.business.repository_impl

import com.tanveer.airlib.task.shared.data.UserEntity
import com.tanveer.airlib.task.shared.data.db.UserDao
import com.tanveer.airlib.task.shared.data.repository.DatabaseRepository

class DatabaseRepositoryImpl(private val dao: UserDao) : DatabaseRepository {
    override suspend fun saveUsername(username: String) {
        val user = UserEntity(username)
        dao.insertUser(user)
    }

    override suspend fun getUsername(): UserEntity {
        return dao.getUser()
    }

    override suspend fun isUsernameExists(username: String): Boolean {
        return dao.isUsernameExists(username)
    }
}