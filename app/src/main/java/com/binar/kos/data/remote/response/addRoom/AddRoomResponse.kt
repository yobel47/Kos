package com.binar.kos.data.remote.response.addRoom

import com.google.gson.annotations.SerializedName

data class AddRoomResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)