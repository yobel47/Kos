package com.binar.kos.data.repository

import com.binar.kos.network.ApiHelper
import com.binar.kos.network2.ApiHelper2

class RoomRepository (private val apiHelper2: ApiHelper2){
    suspend fun getAllRooms() = apiHelper2.getAllRooms()
}