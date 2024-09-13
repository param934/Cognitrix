package com.example.cognitrix.db

import com.example.cognitrix.api.login.ApiService
import com.example.cognitrix.api.login.LoginRequest
import com.example.cognitrix.api.login.RegisterRequest

class UserRepository(private val api: ApiService, private val userDao: UserDao) {

    suspend fun login(username: String, password: String): Result<UserEntity> {
        return try {
            val response = api.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    val userEntity = UserEntity(
                        username = loginResponse.user.username,
                        firstName = loginResponse.user.first_name,
                        lastName = loginResponse.user.last_name,
                        email = loginResponse.user.email,
                        address = loginResponse.user.address,
                        mobile = loginResponse.user.mobile,
                        accessToken = loginResponse.access,
                        refreshToken = loginResponse.refresh
                    )
                    userDao.insertUser(userEntity)
                    Result.success(userEntity)
                } ?: Result.failure(Exception("No data available"))
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun register(registerRequest: RegisterRequest): Result<UserEntity> {
        return try {
            val response = api.registerUser(registerRequest)
            if (response.isSuccessful) {
                response.body()?.let { registerResponse ->
                    // Create UserEntity from registration response
                    val userEntity = UserEntity(
                        username = registerResponse.user.username,
                        firstName = registerResponse.user.first_name,
                        lastName = registerResponse.user.last_name,
                        email = registerResponse.user.email,
                        address = registerResponse.user.address,
                        mobile = registerResponse.user.mobile,
                        accessToken = registerResponse.access,
                        refreshToken = registerResponse.refresh
                    )
                    // Insert the user into the database
                    println(userEntity)
                    userDao.insertUser(userEntity)
                    Result.success(userEntity)
                } ?: Result.failure(Exception("No data available"))
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(): UserEntity? {
        return userDao.getUser()
    }

    suspend fun updateAccessToken(username: String, newAccessToken: String) {
        userDao.updateAccessToken(username, newAccessToken)
    }

    suspend fun updateRefreshToken(username: String, newRefreshToken: String) {
        userDao.updateRefreshToken(username, newRefreshToken)
    }
}
