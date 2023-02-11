package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class EditUserRequest(
    @field:SerializedName("createdAt")
    val createdAt: Long,

    @field:SerializedName("updatedAt")
    val updatedAt: Long,

    @field:SerializedName("namaLengkap")
    val namaLengkap: String,

    @field:SerializedName("gender")
    val gender: String,

    @field:SerializedName("tanggalKelahiran")
    val tanggalKelahiran: String,

    @field:SerializedName("kotaAsal")
    val kotaAsal: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("noTelepon")
    val noTelepon: String,

    @field:SerializedName("profesi")
    val profesi: String,

    @field:SerializedName("bank")
    val bank: String,

    @field:SerializedName("noRekening")
    val noRekening: String,

    @field:SerializedName("pin")
    val pin: String,

    @field:SerializedName("image")
    val image: String,
)