package com.example.cognitrix.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity?

    @Query("UPDATE user SET accessToken = :accessToken WHERE username = :username")
    suspend fun updateAccessToken(username: String, accessToken: String)

    @Query("UPDATE user SET refreshToken = :refreshToken WHERE username = :username")
    suspend fun updateRefreshToken(username: String, refreshToken: String)
}


@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
