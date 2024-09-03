package com.example.cognitrix.api.login

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
    val address: String
)
