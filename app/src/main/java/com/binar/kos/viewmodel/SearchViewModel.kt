package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.repository.SearchRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import org.json.JSONObject
import retrofit2.HttpException

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {
    fun searchRoom(
        params: String?,
        tipekostputra: Int,
        tipekosputri: Int,
        tipekoscampur: Int,
        priceday: Int,
        priceweek: Int,
        pricemonth: Int,
        minprice: Int,
        maxprice: Int,
        bed: Int,
        fan: Int,
        chair: Int,
        table: Int,
        pillow: Int,
        window: Int,
        tvinroom: Int,
        wardrobe: Int,
        hot_water: Int,
        seat_toilet: Int,
        squat_toilet: Int,
        inside_bathroom: Int,
        outside_bathroom: Int,
        air_conditioning: Int,
        electricity: Int,
        iron: Int,
        wifi: Int,
        kitchen: Int,
        loundry: Int,
        mushola: Int,
        dispenser: Int,
        car_parking: Int,
        motorcycle_parking: Int,
        bicycle_parking: Int,
        living_room: Int,
        tvinlivingroom: Int,
        washing_machine: Int,
        refrigerator: Int,
        pet: Int,
        couple: Int,
        person: Int,
        check_in: Int,
        check_out: Int,
        children: Int,
        add_electricity: Int,
        smoking_in_room: Int,
        special_student: Int,
        special_employees: Int,
        twf_hours_access: Int,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.searchRoom(
                params,
                tipekostputra,
                tipekosputri,
                tipekoscampur,
                priceday,
                priceweek,
                pricemonth,
                minprice,
                maxprice,
                bed,
                fan,
                chair,
                table,
                pillow,
                window,
                tvinroom,
                wardrobe,
                hot_water,
                seat_toilet,
                squat_toilet,
                inside_bathroom,
                outside_bathroom,
                air_conditioning,
                electricity,
                iron,
                wifi,
                kitchen,
                loundry,
                mushola,
                dispenser,
                car_parking,
                motorcycle_parking,
                bicycle_parking,
                living_room,
                tvinlivingroom,
                washing_machine,
                refrigerator,
                pet,
                couple,
                person,
                check_in,
                check_out,
                children,
                add_electricity,
                smoking_in_room,
                special_student,
                special_employees,
                twf_hours_access)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

}
