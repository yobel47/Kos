package com.binar.kos.data.remote.response.myRoom

import com.google.gson.annotations.SerializedName

data class Approved(

	@field:SerializedName("isApprovalRoom")
	val isApprovalRoom: Boolean? = null,

	@field:SerializedName("statusApprovalRoom")
	val statusApprovalRoom: String? = null
)