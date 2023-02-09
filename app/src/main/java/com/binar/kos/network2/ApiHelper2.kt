package com.binar.kos.network2

import com.binar.kos.data.remote.request.AddRoomRequest
import com.binar.kos.data.remote.request.LoginRequest
import com.binar.kos.data.remote.request.RegisterRequest
import com.binar.kos.network2.ApiService2
import com.binar.kos.utils.getHeaderMap
import okhttp3.RequestBody

class ApiHelper2(private val apiService2: ApiService2) {
    suspend fun getSearch(params: String) = apiService2.fetchSearch(params)
    suspend fun getAllRooms() = apiService2.getAllRooms()
    suspend fun getDetailRoom(id: String) = apiService2.fetchDetailRoom(id)
    suspend fun postRoom(accessToken: String, request: RequestBody) = apiService2.postRoom(
        getHeaderMap(accessToken), request)
}