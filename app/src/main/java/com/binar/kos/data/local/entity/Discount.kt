package com.binar.kos.data.local.entity


import com.google.gson.annotations.SerializedName

data class Discount(
    @SerializedName("discount_percentage")
    var discountPercentage: Int?,
    @SerializedName("is_discount")
    var isDiscount: String?
)