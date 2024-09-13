package com.example.cognitrix.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val address: String,
    val mobile: String,
    val accessToken: String,
    val refreshToken: String
)
