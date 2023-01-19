package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class EditUserRequest(
    @field:SerializedName("nama_lengkap")
    val nama_lengkap: String,

    @field:SerializedName("jenis_kelamin")
    val jenis_kelamin: String,

    @field:SerializedName("tanggal_lahir")
    val tanggal_lahir: String,

    @field:SerializedName("kota_asal")
    val kota_asal: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("no_hp")
    val no_hp: String,

    @field:SerializedName("profesi")
    val profesi: String,
)