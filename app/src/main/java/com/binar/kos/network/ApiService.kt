package com.binar.kos.network

import com.binar.kos.data.local.entity.Kos
import com.binar.kos.data.remote.request.*
import com.binar.kos.data.remote.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/signup")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

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

    @GET("/room")
    suspend fun getAllRooms() : ArrayList<Kos>

}
