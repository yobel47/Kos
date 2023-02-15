package com.binar.kos.data.repository

import com.binar.kos.data.remote.request.EditUserRequest
import com.binar.kos.network.ApiHelper

class UserRepository(private val apiHelper: ApiHelper) {
    suspend fun getUser(accessToken: String) =
        apiHelper.getUser(accessToken)

    suspend fun editUser(accessToken: String, request: EditUserRequest) =
        apiHelper.editUser(accessToken, request)


}