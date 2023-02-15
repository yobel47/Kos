package com.binar.kos.data.remote.response.historyPenyewa

import com.google.gson.annotations.SerializedName

data class RentalAgreement(

	@field:SerializedName("description_approval_booking")
	val descriptionApprovalBooking: String? = null,

	@field:SerializedName("statusBooking")
	val statusBooking: String? = null,

	@field:SerializedName("isApprovalBooking")
	val isApprovalBooking: Boolean? = null
)