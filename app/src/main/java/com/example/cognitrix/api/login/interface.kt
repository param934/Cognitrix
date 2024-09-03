package com.example.cognitrix.api.login

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/login/")
    suspend fun login(@Body requestBody: LoginRequest): Response<LoginResponse>
}
