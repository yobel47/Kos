package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("otp")
    val otp: String,

    @field:SerializedName("newPassword")
    val newPassword: String,
)