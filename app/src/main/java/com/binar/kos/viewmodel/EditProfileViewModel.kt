package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.remote.request.EditUserRequest
import com.binar.kos.data.repository.UserRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class EditProfileViewModel(private val repository: UserRepository) : ViewModel() {

    fun editThisUser(request: EditUserRequest, accessToken: String) =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            try {
                emit(Resource.success(data = repository.editUser(accessToken, request)))
            } catch (e: IOException) {
                emit(Resource.error(null, e.message ?: "Error Occurred!"))
            } catch (e: HttpException) {
                val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
                emit(Resource.error(null, jsonObj.getString("message") ?: "Error occurred!"))
            }
        }
}