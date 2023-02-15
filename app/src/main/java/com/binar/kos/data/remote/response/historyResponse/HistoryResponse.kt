package com.binar.kos.data.remote.response.historyResponse

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("total_cost")
	val totalCost: Int? = null,

	@field:SerializedName("voucher")
	val voucher: Any? = null,

	@field:SerializedName("booking_date")
	val bookingDate: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("booking_id")
	val bookingId: Int? = null,

	@field:SerializedName("promo")
	val promo: Any? = null,

	@field:SerializedName("duration")
	val duration: Any? = null,

	@field:SerializedName("date_transaction")
	val dateTransaction: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("price")
	val price: Price? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: List<ImageUrlItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("rental_date")
	val rentalDate: String? = null,

	@field:SerializedName("type_cost")
	val typeCost: String? = null,

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("wide")
	val wide: String? = null,

	@field:SerializedName("method")
	val method: String? = null,

	@field:SerializedName("room_cost")
	val roomCost: Int? = null,

	@field:SerializedName("status_transaction")
	val statusTransaction: StatusTransaction? = null,

	@field:SerializedName("electricity")
	val electricity: Boolean? = null,

	@field:SerializedName("services")
	val services: Int? = null,

	@field:SerializedName("approval_transaction")
	val approvalTransaction: ApprovalTransaction? = null,

	@field:SerializedName("deposit")
	val deposit: Any? = null,

	@field:SerializedName("facilities")
	val facilities: Facilities? = null
)