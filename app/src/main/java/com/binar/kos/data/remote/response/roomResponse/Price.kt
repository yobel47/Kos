package com.binar.kos.data.remote.response.roomResponse

import com.google.gson.annotations.SerializedName

data class Price(

    @field:SerializedName("cost_month")
    val costMonth: String? = null,

    @field:SerializedName("cost_week")
    val costWeek: String? = null,

    @field:SerializedName("cost_day")
    val costDay: String? = null
)