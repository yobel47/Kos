package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class ApprovalRequest(
    @field:SerializedName("is_approval_booking")
    val title: String,
    @field:SerializedName("status_booking")
    val type: String,
    @field:SerializedName("description_approval_booking")
    val stock: String,
)
