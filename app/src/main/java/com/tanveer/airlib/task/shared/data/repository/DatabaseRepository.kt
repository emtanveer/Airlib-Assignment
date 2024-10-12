package com.tanveer.airlib.task.shared.data.repository

import com.tanveer.airlib.task.shared.data.UserEntity

interface DatabaseRepository {
    suspend fun saveUsername(username: String)
    suspend fun getUsername(): UserEntity
    suspend fun isUsernameExists(username: String): Boolean
}