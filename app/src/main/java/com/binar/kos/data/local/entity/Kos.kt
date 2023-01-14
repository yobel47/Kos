package com.binar.kos.data.local.entity

enum class TipeKos{
    Campur,
    Pria,
    Wanita
}

data class Kos(
    var id:String,
    var name: String,
    var kota: String,
    var rating: Double,
    var discount: Int,
    var originalPrice: Int,
    var discountedPrice: Int,
    var tipeKos: String,
    var kosImage: String,
)