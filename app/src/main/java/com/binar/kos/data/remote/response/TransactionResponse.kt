package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class TransactionResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
