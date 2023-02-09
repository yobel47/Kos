package com.binar.kos.data.remote.response.addRoom

import com.google.gson.annotations.SerializedName

data class Approved(

	@field:SerializedName("isApprovalRoom")
	val isApprovalRoom: Boolean? = null,

	@field:SerializedName("statusApprovalRoom")
	val statusApprovalRoom: String? = null
)