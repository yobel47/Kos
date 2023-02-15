package com.binar.kos.data.remote.response.historyPenyewa

import com.google.gson.annotations.SerializedName

data class Payments(

	@field:SerializedName("method")
	val method: String? = null,

	@field:SerializedName("bank_type")
	val bankType: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)