package com.binar.kos.data.repository

import com.binar.kos.data.remote.request.AddRoomRequest
import com.binar.kos.network.ApiHelper
import com.binar.kos.network2.ApiHelper2
import okhttp3.RequestBody

class RoomRepository(private val apiHelper2: ApiHelper2) {
    suspend fun getAllRooms() = apiHelper2.getAllRooms()
    suspend fun getDetailRoom(id: String) = apiHelper2.getDetailRoom(id)
    suspend fun addRoom(accessToken: String, request: RequestBody) = apiHelper2.postRoom(accessToken, request)
}