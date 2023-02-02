package com.binar.kos.data.local.entity

data class Address (
    var id: Int,
    var street: String,
    var village: String,
    var district: String,
    var city: String,
    var province: String,
    var postal_code: String,
    var room_id: String,
    var createdAt: String,
    var updatedAt: String,
)