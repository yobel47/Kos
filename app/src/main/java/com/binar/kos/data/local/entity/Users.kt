package com.binar.kos.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "nama_lengkap") val nama_lengkap: String? = null,
    @ColumnInfo(name = "email") val email: String? = null,
    @ColumnInfo(name = "password") val password: String? = null,
    @ColumnInfo(name = "jenis_kelamin") val jenis_kelamin: String? = null,
    @ColumnInfo(name = "tanggal_lahir") val tanggal_lahir: String? = null,
    @ColumnInfo(name = "kota_asal") val kota_asal: String? = null,
    @ColumnInfo(name = "status") val status: String? = null,
    @ColumnInfo(name = "no_hp") val no_hp: String? = null,
    @ColumnInfo(name = "profesi") val profesi: String? = null,
)