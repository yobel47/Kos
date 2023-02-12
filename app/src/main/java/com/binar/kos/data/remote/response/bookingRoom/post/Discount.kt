package com.binar.kos.data.remote.response.bookingRoom.post

import com.google.gson.annotations.SerializedName

data class Discount(

	@field:SerializedName("discount_percentage")
	val discountPercentage: Int? = null,

	@field:SerializedName("is_discount")
	val isDiscount: Boolean? = null
)