package com.binar.kos.data.remote.response.roomResponse

import com.google.gson.annotations.SerializedName

data class UserDetail(

    @field:SerializedName("bank")
    val bank: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("kota_asal")
    val kotaAsal: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("nama_lengkap")
    val namaLengkap: String? = null,

    @field:SerializedName("no_rekening")
    val noRekening: String? = null,

    @field:SerializedName("profesi")
    val profesi: String? = null,

    @field:SerializedName("tanggal_lahir")
    val tanggalLahir: String? = null,

    @field:SerializedName("no_telepon")
    val noTelepon: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("image")
    val image: String? = null,
)
