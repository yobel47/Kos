package com.binar.kos.data.repository

import com.binar.kos.network.ApiHelper

class LogoutRepository(private val apiHelper: ApiHelper) {
    suspend fun getUserdata(accessToken: String) =
        apiHelper.getUser(accessToken)


}