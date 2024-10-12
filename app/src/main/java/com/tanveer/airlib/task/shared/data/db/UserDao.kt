package com.tanveer.airlib.task.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tanveer.airlib.task.shared.data.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users LIMIT 1")
    suspend fun getUser(): UserEntity

    @Query("SELECT EXISTS(SELECT 1 FROM users WHERE username = :username)")
    suspend fun isUsernameExists(username: String): Boolean
}