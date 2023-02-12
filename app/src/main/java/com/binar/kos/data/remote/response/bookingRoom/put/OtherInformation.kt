package com.binar.kos.data.remote.response.bookingRoom.put

import com.google.gson.annotations.SerializedName

data class OtherInformation(

	@field:SerializedName("with_children")
	val withChildren: String? = null,

	@field:SerializedName("with_couple")
	val withCouple: String? = null
)