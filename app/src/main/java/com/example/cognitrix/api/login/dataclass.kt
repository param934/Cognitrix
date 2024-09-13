package com.example.cognitrix.api.login

import android.net.Uri

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val user: User,
    val access: String,
    val refresh: String
)

data class User(
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val mobile: String,
    val address: String
)
data class RegisterRequest(
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val password: String,
    val password2: String,
    val address: String,
    val mobile: String,

    val password_based_authentication: Boolean
    // Add profile_pic if needed
)


data class RegisterResponse(
    val refresh: String,
    val access: String,
    val user: User
)