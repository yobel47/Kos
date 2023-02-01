package com.binar.kos.data.repository

import com.binar.kos.network2.ApiHelper2

class SearchRepository(private val apiHelper2: ApiHelper2) {
    suspend fun searchRoom(params: String) =
        apiHelper2.getSearch(params)
}