package com.binar.kos.network2

import com.binar.kos.data.remote.request.LoginRequest
import com.binar.kos.data.remote.request.RegisterRequest
import com.binar.kos.network2.ApiService2

class ApiHelper2(private val apiService2: ApiService2) {
    suspend fun getSearch(params: String) = apiService2.fetchSearch(params)

    suspend fun getAllRooms() = apiService2.getAllRooms()
}