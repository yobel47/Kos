package com.binar.kos.network

import com.binar.kos.data.remote.request.LoginRequest
import com.binar.kos.data.remote.request.RegisterRequest

class ApiHelper(private val apiService: ApiService) {

    suspend fun login(email: String, password:String) = apiService.login(LoginRequest(email, password))

    suspend fun register(email: String, username: String, password: String, fullname: String, role: String) =
        apiService.register(RegisterRequest(email, username, password, fullname, role))

    suspend fun verif(tokenOtp: String) = apiService.confirmUser(tokenOtp)

    suspend fun sendOtp(email: String, username: String, password: String, fullname: String, role: String) =
        apiService.sendOtp(RegisterRequest(email, username, password, fullname, role))

    suspend fun getAllRooms() = apiService.getAllRooms()
}