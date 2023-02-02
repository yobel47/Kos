package com.binar.kos.data.remote.response.userdataResponse

import com.google.gson.annotations.SerializedName

data class UserdataResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

