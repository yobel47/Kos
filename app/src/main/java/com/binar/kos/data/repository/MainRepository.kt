package com.binar.kos.data.repository

import com.binar.kos.network2.ApiHelper2
import okhttp3.RequestBody

class MainRepository(private val apiHelper2: ApiHelper2) {
    suspend fun subscribeToken(accessToken: String, firebaseToken: RequestBody) =
        apiHelper2.subscribeToken(accessToken, firebaseToken)

    suspend fun getNotification(accessToken: String, category: String) =
        apiHelper2.getNotification(accessToken, category)

    suspend fun getHistory(accessToken: String, status: String?) =
        apiHelper2.getHistory(accessToken, status)

    suspend fun getHistoryPenyewa(accessToken: String, status: String?) =
        apiHelper2.getHistoryPenyewa(accessToken, status)

    suspend fun uploadProfile(accessToken: String, request: RequestBody) =
        apiHelper2.uploadProfile(accessToken, request)
}