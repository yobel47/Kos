package com.binar.kos.data.remote.response.bookingRoom.post

import com.google.gson.annotations.SerializedName

data class BookingPostResponse(

	@field:SerializedName("room_id")
	val roomId: Int? = null,

	@field:SerializedName("note")
	val note: Any? = null,

	@field:SerializedName("other_information")
	val otherInformation: OtherInformation? = null,

	@field:SerializedName("rooms")
	val rooms: Rooms? = null,

	@field:SerializedName("total_cost")
	val totalCost: Int? = null,

	@field:SerializedName("booking_date")
	val bookingDate: String? = null,

	@field:SerializedName("voucher")
	val voucher: Any? = null,

	@field:SerializedName("room_cost")
	val roomCost: Int? = null,

	@field:SerializedName("pemilik_id")
	val pemilikId: Int? = null,

	@field:SerializedName("check_document")
	val checkDocument: Boolean? = null,

	@field:SerializedName("services")
	val services: Int? = null,

	@field:SerializedName("promo")
	val promo: Any? = null,

	@field:SerializedName("duration")
	val duration: Any? = null,

	@field:SerializedName("total_user")
	val totalUser: Int? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("detail_user")
	val detailUser: DetailUser? = null,

	@field:SerializedName("deposit")
	val deposit: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("rental_date")
	val rentalDate: String? = null,

	@field:SerializedName("type_cost")
	val typeCost: String? = null
)