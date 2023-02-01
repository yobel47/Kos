package com.binar.kos.data.local.entity


import com.google.gson.annotations.SerializedName

data class Facilities(
    @SerializedName("air_conditioning")
    var airConditioning: String?,
    @SerializedName("bathtub")
    var bathtub: String?,
    @SerializedName("bed")
    var bed: String?,
    @SerializedName("bicycle_parking")
    var bicycleParking: String?,
    @SerializedName("car_parking")
    var carParking: String?,
    @SerializedName("chair")
    var chair: String?,
    @SerializedName("dispenser")
    var dispenser: String?,
    @SerializedName("fan")
    var fan: String?,
    @SerializedName("hot_water")
    var hotWater: String?,
    @SerializedName("inside_bathroom")
    var insideBathroom: String?,
    @SerializedName("kitchen")
    var kitchen: String?,
    @SerializedName("living_room")
    var livingRoom: String?,
    @SerializedName("mirror")
    var mirror: String?,
    @SerializedName("motorcycle_parking")
    var motorcycleParking: String?,
    @SerializedName("outside_bathroom")
    var outsideBathroom: String?,
    @SerializedName("pillow")
    var pillow: String?,
    @SerializedName("refrigerator")
    var refrigerator: String?,
    @SerializedName("shower")
    var shower: String?,
    @SerializedName("sink")
    var sink: String?,
    @SerializedName("table")
    var table: String?,
    @SerializedName("tv")
    var tv: String?,
    @SerializedName("ventilation")
    var ventilation: String?,
    @SerializedName("wardrobe")
    var wardrobe: String?,
    @SerializedName("washing_machine")
    var washingMachine: String?,
    @SerializedName("wifi")
    var wifi: String?,
    @SerializedName("window")
    var window: String?
)