package com.binar.kos.data.local.entity

import com.google.gson.annotations.SerializedName

data class Kos(
    @SerializedName("address")
    var address: Address?,
    @SerializedName("createdBy")
    var createdBy: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("discount")
    var discount: Discount?,
    @SerializedName("electricity")
    var electricity: Boolean?,
    @SerializedName("facilities")
    var facilities: Facilities?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("imageUrl")
    var imageUrl: List<ImageUrl>?,
    @SerializedName("price")
    var price: Price?,
    @SerializedName("rules")
    var rules: Rules?,
    @SerializedName("stock")
    var stock: Int?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("updatedAt")
    var updatedAt: String?,
    @SerializedName("wide")
    var wide: String?,
)