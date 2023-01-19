package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("access_token")
    val email: String,

    @field:SerializedName("refresh_token")
    val password: String,

    @field:SerializedName("scope")
    val token: String,

    @field:SerializedName("token_type")
    val token_type: String,

    @field:SerializedName("expires_in")
    val expires_in: Int,

    @field:SerializedName("jti")
    val jti: String,

//    @field:SerializedName("email")
//    val email: String,
//
//    @field:SerializedName("password")
//    val password: String,
//
//    @field:SerializedName("token")
//    val token: String,
)