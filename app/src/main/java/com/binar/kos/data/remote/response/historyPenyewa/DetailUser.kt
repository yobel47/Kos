package com.binar.kos.data.remote.response.historyPenyewa

import com.google.gson.annotations.SerializedName

data class DetailUser(

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("kota_asal")
	val kotaAsal: String? = null,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String? = null,

	@field:SerializedName("profesi")
	val profesi: String? = null,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("no_telepon")
	val noTelepon: String? = null
)