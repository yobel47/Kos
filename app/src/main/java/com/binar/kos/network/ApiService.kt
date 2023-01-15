package com.binar.kos.network

import com.binar.kos.data.remote.request.LoginRequest
import com.binar.kos.data.remote.request.RegisterRequest
import com.binar.kos.data.remote.response.LoginResponse
import com.binar.kos.data.remote.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/signup")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}
