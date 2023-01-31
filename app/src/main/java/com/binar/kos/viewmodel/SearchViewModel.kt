package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.repository.LoginRepository
import com.binar.kos.data.repository.SearchRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import org.json.JSONObject
import retrofit2.HttpException

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {
    fun searchRoom(
        params: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.searchRoom(params)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

}
