package com.binar.kos.data.repository

import com.binar.kos.data.remote.request.RegisterRequest
import com.binar.kos.network.ApiHelper
import com.binar.kos.network.ApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody

class RegisterRepository(private val apiHelper: ApiHelper) {
    suspend fun registerAccount(email: String, username: String, password: String, fullname: String, role: String) =
        apiHelper.register(email, username, password, fullname, role)

    suspend fun verifAccount(tokenOtp: String) = apiHelper.verif(tokenOtp)

    suspend fun resendOtp(email: String, username: String, password: String, fullname: String, role: String) =
        apiHelper.sendOtp(email, username, password, fullname, role)
}