package com.binar.kos.data.remote.response.bookingRoom.post

import com.google.gson.annotations.SerializedName

data class Facilities(

	@field:SerializedName("bed")
	val bed: String? = null,

	@field:SerializedName("shower")
	val shower: String? = null,

	@field:SerializedName("mirror")
	val mirror: String? = null,

	@field:SerializedName("air_conditioning")
	val airConditioning: String? = null,

	@field:SerializedName("ventilation")
	val ventilation: String? = null,

	@field:SerializedName("tvinlivingroom")
	val tvinlivingroom: String? = null,

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

	@field:SerializedName("squat_toilet")
	val squatToilet: String? = null,

	@field:SerializedName("tvinroom")
	val tvinroom: String? = null,

	@field:SerializedName("wardrobe")
	val wardrobe: String? = null,

	@field:SerializedName("seat_toilet")
	val seatToilet: String? = null,

	@field:SerializedName("kitchen")
	val kitchen: String? = null,

	@field:SerializedName("table")
	val table: String? = null,

	@field:SerializedName("washing_machine")
	val washingMachine: String? = null,

	@field:SerializedName("loundry")
	val loundry: String? = null,

	@field:SerializedName("wifi")
	val wifi: String? = null,

	@field:SerializedName("sink")
	val sink: String? = null,

	@field:SerializedName("chair")
	val chair: String? = null,

	@field:SerializedName("outside_bathroom")
	val outsideBathroom: String? = null,

	@field:SerializedName("mushola")
	val mushola: String? = null,

	@field:SerializedName("bicycle_parking")
	val bicycleParking: String? = null,

	@field:SerializedName("pillow")
	val pillow: String? = null,

	@field:SerializedName("inside_bathroom")
	val insideBathroom: String? = null,

	@field:SerializedName("iron")
	val iron: String? = null,

	@field:SerializedName("window")
	val window: String? = null,

	@field:SerializedName("hot_water")
	val hotWater: String? = null,

	@field:SerializedName("refrigerator")
	val refrigerator: String? = null
)