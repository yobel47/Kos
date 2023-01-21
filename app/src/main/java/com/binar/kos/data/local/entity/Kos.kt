package com.binar.kos.data.local.entity


data class Kos(
    var id: Int,
    var title: String,
    var type: String,
    var stock: String,
    var price: Int,
    var imageUrl: List<String>,
    var description: String,
    var electricity: Boolean,
    var wide: String,
    var user_id: Int,
    var createdBy: String,
    var createdAt: String,
    var updatedAt: String,
)