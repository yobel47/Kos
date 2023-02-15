package com.binar.kos.data.remote.response.historyResponse

import com.google.gson.annotations.SerializedName

data class StatusTransaction(

	@field:SerializedName("isSucces")
	val isSucces: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)