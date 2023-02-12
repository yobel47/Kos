package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class CancelTransactionRequest (
    @field:SerializedName("description")
    val description: String,
)