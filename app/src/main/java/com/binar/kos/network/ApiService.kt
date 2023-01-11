package com.binar.kos.network

import com.binar.kos.data.remote.request.LoginRequest
import com.binar.kos.data.remote.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
