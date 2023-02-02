package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.repository.RegisterRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel(private val repository: RegisterRepository) : ViewModel() {

    fun registerAccount(
        email: String,
        username: String,
        password: String,
        fullname: String,
        role : String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.registerAccount(email, username, password, fullname, role)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }


    fun verifAccount(
        tokenOtp: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.verifAccount(tokenOtp)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun resendOtp(
        email: String,
        username: String,
        password: String,
        fullname: String,
        role : String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.resendOtp(email, username, password, fullname, role)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }
}