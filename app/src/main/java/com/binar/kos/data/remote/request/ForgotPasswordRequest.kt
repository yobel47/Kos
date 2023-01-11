package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class ForgotPasswordRequest(
    @field:SerializedName("email")
    val email: String,
)
