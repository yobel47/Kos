package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("access_token")
    val access_token: String,

    @field:SerializedName("refresh_token")
    val refresh_token: String,

    @field:SerializedName("scope")
    val scope: String,

    @field:SerializedName("token_type")
    val token_type: String,

    @field:SerializedName("expires_in")
    val expires_in: Int,

    @field:SerializedName("jti")
    val jti: String,
)

