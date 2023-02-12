package com.binar.kos.data.remote.response.paymentMethod

import com.google.gson.annotations.SerializedName

data class PaymentMethodResponseItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("method")
    val method: String? = null,

    @field:SerializedName("bank_type")
    val bankType: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("content")
    val content: String? = null,

    @field:SerializedName("picture")
    val picture: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
