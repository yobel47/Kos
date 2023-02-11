package com.binar.kos.data.repository

import com.binar.kos.data.remote.request.EditUserRequest
import com.binar.kos.network.ApiHelper

class UserRepository(private val apiHelper: ApiHelper) {
    suspend fun getUser(accessToken: String) =
        apiHelper.getUser(accessToken)

    suspend fun editUser(id: Int, accessToken: String, request: EditUserRequest) =
        apiHelper.editUser(id, accessToken, request)
}