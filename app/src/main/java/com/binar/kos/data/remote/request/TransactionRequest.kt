package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName

data class TransactionRequest (
    @field:SerializedName("payment_id")
    val paymentId: Int,
)