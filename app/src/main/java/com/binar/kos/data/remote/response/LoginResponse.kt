package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("refresh_token")
    val refreshToken: String? = null,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("scope")
    val scope: String? = null,

    @field:SerializedName("token_type")
    val tokenType: String? = null,

    @field:SerializedName("expires_in")
    val expiresIn: Int? = null,

    @field:SerializedName("jti")
    val jti: String? = null

//    @field:SerializedName("email")
//    val email: String,
//
//    @field:SerializedName("password")
//    val password: String,
//
//    @field:SerializedName("token")
//    val token: String,
)