package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.repository.RoomRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel (private val repository: RoomRepository) : ViewModel(){
    fun getAllRooms() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try{
            emit(Resource.success(data = repository.getAllRooms()))
        } catch (e: IOException){
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException){
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error occurred!"))
        }
    }
}