package com.binar.kos.data.remote.response.bookingRoom.post

import com.google.gson.annotations.SerializedName

data class ImageUrlItem(

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)