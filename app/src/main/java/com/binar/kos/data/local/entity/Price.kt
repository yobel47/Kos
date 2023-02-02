package com.binar.kos.data.local.entity

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("cost_day")
    var costDay: String?,
    @SerializedName("cost_month")
    var costMonth: String?,
    @SerializedName("cost_week")
    var costWeek: String?,
)