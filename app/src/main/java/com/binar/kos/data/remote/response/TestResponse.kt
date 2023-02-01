package com.binar.kos.data.remote.response

import com.google.gson.annotations.SerializedName

data class TestResponse(

	@field:SerializedName("TestResponse")
	val testResponse: List<TestResponseItem?>? = null
)

data class Address(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("street")
	val street: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("village")
	val village: String? = null,

	@field:SerializedName("postal_code")
	val postalCode: String? = null
)

data class Price(

	@field:SerializedName("cost_month")
	val costMonth: String? = null,

	@field:SerializedName("cost_week")
	val costWeek: String? = null,

	@field:SerializedName("cost_day")
	val costDay: String? = null
)

data class Discount(

	@field:SerializedName("discount_percentage")
	val discountPercentage: String? = null,

	@field:SerializedName("is_discount")
	val isDiscount: String? = null
)

data class Facilities(

	@field:SerializedName("bed")
	val bed: String? = null,

	@field:SerializedName("shower")
	val shower: String? = null,

	@field:SerializedName("tv")
	val tv: String? = null,

	@field:SerializedName("mirror")
	val mirror: String? = null,

	@field:SerializedName("air_conditioning")
	val airConditioning: String? = null,

	@field:SerializedName("ventilation")
	val ventilation: String? = null,

	@field:SerializedName("car_parking")
	val carParking: String? = null,

	@field:SerializedName("dispenser")
	val dispenser: String? = null,

	@field:SerializedName("motorcycle_parking")
	val motorcycleParking: String? = null,

	@field:SerializedName("fan")
	val fan: String? = null,

	@field:SerializedName("bathtub")
	val bathtub: String? = null,

	@field:SerializedName("living_room")
	val livingRoom: String? = null,

	@field:SerializedName("wardrobe")
	val wardrobe: String? = null,

	@field:SerializedName("kitchen")
	val kitchen: String? = null,

	@field:SerializedName("table")
	val table: String? = null,

	@field:SerializedName("washing_machine")
	val washingMachine: String? = null,

	@field:SerializedName("wifi")
	val wifi: String? = null,

	@field:SerializedName("sink")
	val sink: String? = null,

	@field:SerializedName("chair")
	val chair: String? = null,

	@field:SerializedName("outside_bathroom")
	val outsideBathroom: String? = null,

	@field:SerializedName("bicycle_parking")
	val bicycleParking: String? = null,

	@field:SerializedName("pillow")
	val pillow: String? = null,

	@field:SerializedName("inside_bathroom")
	val insideBathroom: String? = null,

	@field:SerializedName("window")
	val window: String? = null,

	@field:SerializedName("hot_water")
	val hotWater: String? = null,

	@field:SerializedName("refrigerator")
	val refrigerator: String? = null
)

data class Rules(

	@field:SerializedName("couple")
	val couple: String? = null,

	@field:SerializedName("children")
	val children: String? = null,

	@field:SerializedName("person")
	val person: String? = null,

	@field:SerializedName("deposit")
	val deposit: String? = null
)

data class TestResponseItem(

	@field:SerializedName("address")
	val address: Address? = null,

	@field:SerializedName("wide")
	val wide: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("discount")
	val discount: Discount? = null,

	@field:SerializedName("electricity")
	val electricity: Boolean? = null,

	@field:SerializedName("rules")
	val rules: Rules? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("createdBy")
	val createdBy: String? = null,

	@field:SerializedName("price")
	val price: Price? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: List<ImageUrlItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("stock")
	val stock: Int? = null,

	@field:SerializedName("facilities")
	val facilities: Facilities? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ImageUrlItem(

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)
