package com.binar.kos.network2

import com.binar.kos.data.local.entity.Kos
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import retrofit2.http.*


interface ApiService2 {
    @GET("search")
    suspend fun fetchSearch(
        @Query("keyword") params: String,
    ): List<SearchResponse>

    @GET("/api/room")
    suspend fun getAllRooms() : ArrayList<Kos>
}
