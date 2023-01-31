package com.binar.kos.data.remote.response.searchResponse

import com.google.gson.annotations.SerializedName

data class Discount(

    @field:SerializedName("discount_percentage")
    val discountPercentage: String? = null,

    @field:SerializedName("is_discount")
    val isDiscount: String? = null
)