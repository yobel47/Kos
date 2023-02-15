package com.binar.kos.data.remote.response.historyPenyewa

import com.google.gson.annotations.SerializedName

data class OtherInformation(

	@field:SerializedName("with_children")
	val withChildren: String? = null,

	@field:SerializedName("with_couple")
	val withCouple: String? = null
)