package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.repository.LoginRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun loginAccount(
        email: String, password: String
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.loginAccount(email, password)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            emit(Resource.error(null, e.message() ?: "Error Occurred!"))
        }
    }

}