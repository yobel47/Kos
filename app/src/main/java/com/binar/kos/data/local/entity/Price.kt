package com.binar.kos.data.local.entity

data class Price(
    var id: Int,
    var cost: Int,
    var type: String,
    var room_id: Int,
    var createdAt: String,
    var updatedAt: String,
)