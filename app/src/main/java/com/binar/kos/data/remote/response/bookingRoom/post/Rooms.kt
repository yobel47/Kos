package com.binar.kos.data.remote.response.bookingRoom.post

import com.google.gson.annotations.SerializedName

data class Rooms(

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("wide")
	val wide: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("discount")
	val discount: Discount? = null,

	@field:SerializedName("electricity")
	val electricity: Boolean? = null,

	@field:SerializedName("rules")
	val rules: Rules? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("price")
	val price: Price? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: List<ImageUrlItem?>? = null,

	@field:SerializedName("admin_id")
	val adminId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("facilities")
	val facilities: Facilities? = null
)