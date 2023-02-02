package com.binar.kos.data.remote.response.userdataResponse

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("gender")
    val gender: Any? = null,

    @field:SerializedName("noRekening")
    val noRekening: Any? = null,

    @field:SerializedName("profesi")
    val profesi: Any? = null,

    @field:SerializedName("createdAt")
    val createdAt: Any? = null,

    @field:SerializedName("tanggalKelahiran")
    val tanggalKelahiran: Any? = null,

    @field:SerializedName("bank")
    val bank: Any? = null,

    @field:SerializedName("noTelepon")
    val noTelepon: Any? = null,

    @field:SerializedName("kotaAsal")
    val kotaAsal: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("namaLengkap")
    val namaLengkap: String? = null,

    @field:SerializedName("tanggalLahiranFormatted")
    val tanggalLahiranFormatted: Any? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: Any? = null,

    @field:SerializedName("status")
    val status: Any? = null
)
