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
    var rating: Float,
    var discount: Int,
    var originalPrice: Int,
    var DiscountedPrice: Int,
    var tipeKos: TipeKos,
)