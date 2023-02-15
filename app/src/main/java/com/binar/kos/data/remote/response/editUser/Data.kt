package com.binar.kos.data.remote.response.editUser

import com.google.gson.annotations.SerializedName

data class Data(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("noRekening")
	val noRekening: String? = null,

	@field:SerializedName("profesi")
	val profesi: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: Any? = null,

	@field:SerializedName("tanggalKelahiran")
	val tanggalKelahiran: Any? = null,

	@field:SerializedName("bank")
	val bank: String? = null,

	@field:SerializedName("noTelepon")
	val noTelepon: String? = null,

	@field:SerializedName("pin")
	val pin: String? = null,

	@field:SerializedName("kotaAsal")
	val kotaAsal: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("namaLengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("tanggalLahiranFormatted")
	val tanggalLahiranFormatted: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)