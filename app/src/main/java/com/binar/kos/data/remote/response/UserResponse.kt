package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

class UserResponse (
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("nama_lengkap")
    val nama_lengkap: Int,
    @field:SerializedName("email")
    val email: Int,
    @field:SerializedName("password")
    val password: Int,
    @field:SerializedName("jenis_kelamin")
    val jenis_kelamin: Int,
    @field:SerializedName("tanggal_lahir")
    val tanggal_lahir: Int,
    @field:SerializedName("kota_asal")
    val kota_asal: Int,
    @field:SerializedName("status")
    val status: Int,
    @field:SerializedName("no_hp")
    val no_hp: Int,
    @field:SerializedName("profesi")
    val profesi: Int,
)
