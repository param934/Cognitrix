package com.example.cognitrix.db

import com.example.cognitrix.api.login.ApiService
import com.example.cognitrix.api.login.LoginRequest

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
                        accessToken = loginResponse.access,
                        refreshToken = loginResponse.refresh
                    )
                    userDao.insertUser(userEntity)
                    Result.success(userEntity)
                } ?: Result.failure(Exception("No data available"))
            } else {
                Result.failure(Exception("Login failed"))

            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUser(username: String): UserEntity? {
        return userDao.getUser(username)
    }

    suspend fun updateAccessToken(username: String, newAccessToken: String) {
        userDao.updateAccessToken(username, newAccessToken)
    }

    suspend fun updateRefreshToken(username: String, newRefreshToken: String) {
        userDao.updateRefreshToken(username, newRefreshToken)
    }
}
