package com.binar.kos.data.repository

import com.binar.kos.network.ApiHelper

class RoomRepository (private val apiHelper: ApiHelper){
    suspend fun getAllRooms() = apiHelper.getAllRooms()
}