package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("is_read")
	val isRead: Boolean? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("token_notification_id")
	val tokenNotificationId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
