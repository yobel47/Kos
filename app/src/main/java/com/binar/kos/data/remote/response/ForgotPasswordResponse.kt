package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class ForgotPasswordResponse(
    @field:SerializedName("data")
    val data: Any,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("code")
    val code: Int,
)