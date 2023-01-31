package com.binar.kos.data.remote.response.searchResponse

import com.google.gson.annotations.SerializedName

data class Rules(
    @field:SerializedName("couple")
    val couple: String? = null,

    @field:SerializedName("children")
    val children: String? = null,

    @field:SerializedName("person")
    val person: String? = null,

    @field:SerializedName("deposit")
    val deposit: String? = null
)

