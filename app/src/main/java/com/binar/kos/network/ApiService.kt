package com.binar.kos.network

import com.binar.kos.data.remote.request.*
import com.binar.kos.data.remote.response.*
import com.binar.kos.data.remote.response.editUser.EditUserResponse
import com.binar.kos.data.remote.response.userdataResponse.UserdataResponse
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/signup")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("auth/index/{tokenotp}")
    suspend fun confirmUser(@Path("tokenotp") tokenotp: String): UsersListResponse

    @POST("auth/send-otp")
    suspend fun sendOtp(@Body request: RegisterRequest): RegisterResponse


    @POST("user/update")
    suspend fun editUser(
        @Body request: EditUserRequest,
        @HeaderMap header: Map<String, String>,
    ): EditUserResponse

    @GET("user/find")
    suspend fun getUser(@HeaderMap header: Map<String, String>): UserdataResponse
}

