package com.tanveer.airlib.task.shared.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tanveer.airlib.task.shared.data.UserEntity

@Database(entities = [UserEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loginUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "problem_listing_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}