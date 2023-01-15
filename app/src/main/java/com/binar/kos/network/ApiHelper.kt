package com.binar.kos.network

import com.binar.kos.data.remote.request.RegisterRequest

class ApiHelper(private val apiService: ApiService) {

    suspend fun register(email: String, username: String, password: String, fullname: String) =
        apiService.register(RegisterRequest(email, username, password, fullname))
}