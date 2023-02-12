package com.binar.kos.data.remote.response.paymentMethod

import com.google.gson.annotations.SerializedName

data class PaymentMethodResponse(

	@field:SerializedName("")
	val paymentMethodResponse: List<PaymentMethodResponseItem>
)

