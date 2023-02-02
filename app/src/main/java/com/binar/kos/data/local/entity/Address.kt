package com.binar.kos.data.local.entity


import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    var city: String?,
    @SerializedName("district")
    var district: String?,
    @SerializedName("postal_code")
    var postalCode: String?,
    @SerializedName("province")
    var province: String?,
    @SerializedName("street")
    var street: String?,
    @SerializedName("village")
    var village: String?
)