package com.binar.kos.network2

import com.binar.kos.data.local.entity.Kos
import com.binar.kos.data.remote.request.AddRoomRequest
import com.binar.kos.data.remote.response.addRoom.AddRoomResponse
import com.binar.kos.data.remote.response.roomResponse.RoomResponse
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService2 {
    @GET("search")
    suspend fun fetchSearch(
        @Query("keyword") params: String,
    ): List<SearchResponse>

    @GET("/api/room")
    suspend fun getAllRooms() : ArrayList<Kos>

    @GET("detail")
    suspend fun fetchDetailRoom(
        @Query("id") params: String,
    ): RoomResponse

    @POST("/api/room")
    suspend fun postRoom(
        @HeaderMap header: Map<String, String>,
        @Body request: RequestBody
    ): AddRoomResponse
}
