package com.binar.kos.data.local.entity


import com.google.gson.annotations.SerializedName

data class ImageUrl(
    @SerializedName("filename")
    var filename: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("url")
    var url: String?
)