package com.binar.kos.data.remote.response.myRoom

import com.google.gson.annotations.SerializedName

data class Address(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("street")
	val street: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null
)