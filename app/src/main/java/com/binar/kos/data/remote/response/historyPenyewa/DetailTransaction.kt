package com.binar.kos.data.remote.response.historyPenyewa

import com.google.gson.annotations.SerializedName

data class DetailTransaction(

	@field:SerializedName("rental_amount")
	val rentalAmount: Int? = null,

	@field:SerializedName("date_transaction")
	val dateTransaction: String? = null,

	@field:SerializedName("payment_id")
	val paymentId: Int? = null,

	@field:SerializedName("admin_id")
	val adminId: Any? = null,

	@field:SerializedName("payments")
	val payments: Payments? = null,

	@field:SerializedName("pemilik_id")
	val pemilikId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("total_payment")
	val totalPayment: Int? = null
)