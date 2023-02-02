package com.binar.kos.data.local.entity


import com.google.gson.annotations.SerializedName

data class Rules(
    @SerializedName("children")
    var children: String?,
    @SerializedName("couple")
    var couple: String?,
    @SerializedName("deposit")
    var deposit: String?,
    @SerializedName("person")
    var person: String?
)