package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class UsersListResponse (
    @SerializedName("users")
    val users: List<UserResponse>,
)