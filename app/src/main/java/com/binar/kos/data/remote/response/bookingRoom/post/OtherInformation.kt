package com.binar.kos.data.remote.response.bookingRoom.post

import com.google.gson.annotations.SerializedName

data class OtherInformation(

	@field:SerializedName("with_children")
	val withChildren: Boolean? = null,

	@field:SerializedName("with_couple")
	val withCouple: Boolean? = null
)