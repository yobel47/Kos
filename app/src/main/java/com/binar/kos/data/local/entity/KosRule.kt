package com.binar.kos.data.local.entity

data class KosRule(
    var id: Int,
    var person: Int,
    var deposit: Int,
    var couple: Boolean,
    var children: Boolean,
    var room_id: Int,
    var createdAt: String,
    var updatedAt: String,
)