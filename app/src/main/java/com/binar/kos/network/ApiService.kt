package com.binar.kos.network

import com.binar.kos.data.local.entity.Kos
import com.binar.kos.data.remote.request.*
import com.binar.kos.data.remote.response.*
import com.binar.kos.data.remote.response.userdataResponse.UserdataResponse
import retrofit2.Call
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

    @POST("auth/forget-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse

    @POST("auth/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): ChangePasswordResponse

    @GET("users/{id}")
    suspend fun getAllUsers(@Path("id") id: Int, @Header("Authorization") authorization: String): UsersListResponse

    @PATCH("users/{id}")
    suspend fun editUser(@Path("id") id: Int, @Body request: EditUserRequest, @Header("Authorization") authorization: String): UserResponse

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int, @Header("Authorization") authorization: String): UserResponse

    @GET("user/find")
    suspend fun getUser(@HeaderMap header: Map<String, String>) : UserdataResponse
}

