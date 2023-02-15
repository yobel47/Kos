package com.binar.kos.data.repository

import com.binar.kos.network.ApiHelper

class LoginRepository(private val apiHelper: ApiHelper) {
    suspend fun loginAccount(email: String, password: String) =
        apiHelper.login(email, password)

}