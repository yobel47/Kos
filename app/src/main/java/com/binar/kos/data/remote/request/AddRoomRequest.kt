package com.binar.kos.data.remote.request

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.io.File

data class AddRoomRequest(

    //data
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("type")
    val type: String,
    @field:SerializedName("stock")
    val stock: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("wide")
    val wide: String,

    //facilities
    //room facilities
    @field:SerializedName("bed")
    val bed: String,
    @field:SerializedName("fan")
    val fan: String,
    @field:SerializedName("chair")
    val chair: String,
    @field:SerializedName("table")
    val table: String,
    @field:SerializedName("pillow")
    val pillow: String,
    @field:SerializedName("window")
    val window: String,
    @field:SerializedName("tvinroom")
    val tvinroom: String,
    @field:SerializedName("wardrobe")
    val wardrobe: String,
    @field:SerializedName("hot_water")
    val hot_water: String,
    @field:SerializedName("seat_toilet")
    val seat_toilet: String,
    @field:SerializedName("squat_toilet")
    val squat_toilet: String,
    @field:SerializedName("inside_bathroom")
    val inside_bathroom: String,
    @field:SerializedName("outside_bathroom")
    val outside_bathroom: String,
    @field:SerializedName("air_conditioning")
    val air_conditioning: String,
    @field:SerializedName("electricity")
    val electricity: String,

    //general facilities
    @field:SerializedName("iron")
    val iron: String,
    @field:SerializedName("wifi")
    val wifi: String,
    @field:SerializedName("kitchen")
    val kitchen: String,
    @field:SerializedName("loundry")
    val loundry: String,
    @field:SerializedName("mushola")
    val mushola: String,
    @field:SerializedName("dispenser")
    val dispenser: String,
    @field:SerializedName("car_parking")
    val car_parking: String,
    @field:SerializedName("motorcycle_parking")
    val motorcycle_parking: String,
    @field:SerializedName("bicycle_parking")
    val bicycle_parking: String,
    @field:SerializedName("living_room")
    val living_room: String,
    @field:SerializedName("tvinlivingroom")
    val tvinlivingroom: String,
    @field:SerializedName("washing_machine")
    val washing_machine: String,
    @field:SerializedName("refrigerator")
    val refrigerator: String,

    //other
    @field:SerializedName("mirror")
    val mirror: String,
    @field:SerializedName("sink")
    val sink: String,
    @field:SerializedName("shower")
    val shower: String,
    @field:SerializedName("bathub")
    val bathub: String,
    @field:SerializedName("ventilation")
    val ventilation: String,

    //rules
    @field:SerializedName("pet")
    val pet: String,
    @field:SerializedName("couple")
    val couple: String,
    @field:SerializedName("person")
    val person: String,
    @field:SerializedName("check_in")
    val check_in: String,
    @field:SerializedName("check_out")
    val check_out: String,
    @field:SerializedName("children")
    val children: String,
    @field:SerializedName("add_electricity")
    val add_electricity: String,
    @field:SerializedName("smoking_in_room")
    val smoking_in_room: String,
    @field:SerializedName("special_student")
    val special_student: String,
    @field:SerializedName("special_employees")
    val special_employees: String,
    @field:SerializedName("twf_hours_access")
    val twf_hours_access: String,

    //address
    @field:SerializedName("street")
    val street: String,
    @field:SerializedName("village")
    val village: String,
    @field:SerializedName("district")
    val district: String,
    @field:SerializedName("city")
    val city: String,
    @field:SerializedName("province")
    val province: String,
    @field:SerializedName("postal_code")
    val postal_code: String,

    //price
    @field:SerializedName("cost_day")
    val cost_day: String,
    @field:SerializedName("cost_week")
    val cost_week: String,
    @field:SerializedName("cost_month")
    val cost_month: String,

    //image
    @field:SerializedName("imageUrl")
    val imageUrl1: MultipartBody.Part,
    @field:SerializedName("imageUrl")
    val imageUrl2: File?,
    @field:SerializedName("imageUrl")
    val imageUrl3: File?,
    @field:SerializedName("imageUrl")
    val imageUrl4: File?,
)
