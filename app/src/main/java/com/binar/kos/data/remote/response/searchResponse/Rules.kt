package com.binar.kos.data.remote.response.searchResponse

import com.google.gson.annotations.SerializedName

data class Rules(

	@field:SerializedName("check_out")
	val checkOut: String? = null,

	@field:SerializedName("couple")
	val couple: String? = null,

	@field:SerializedName("twf_hours_access")
	val twfHoursAccess: String? = null,

	@field:SerializedName("children")
	val children: String? = null,

	@field:SerializedName("smoking_in_room")
	val smokingInRoom: String? = null,

	@field:SerializedName("person")
	val person: String? = null,

	@field:SerializedName("check_in")
	val checkIn: String? = null,

	@field:SerializedName("special_employee")
	val specialEmployee: String? = null,

	@field:SerializedName("pet")
	val pet: String? = null,

	@field:SerializedName("special_student")
	val specialStudent: String? = null,

	@field:SerializedName("add_electricity")
	val addElectricity: String? = null
)