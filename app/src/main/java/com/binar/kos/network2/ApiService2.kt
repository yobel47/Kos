package com.binar.kos.network2

import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import retrofit2.http.*


interface ApiService2 {
    @GET("search")
    suspend fun fetchSearch(
        @Query("keyword") params: String,
    ): List<SearchResponse>

}
