package com.binar.kos.data.remote.response.roomResponse

import com.google.gson.annotations.SerializedName

data class OauthUser(

    @field:SerializedName("user_detail")
    val userDetail: UserDetail? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)