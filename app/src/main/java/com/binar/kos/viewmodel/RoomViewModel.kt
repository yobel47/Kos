package com.binar.kos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.binar.kos.data.repository.RoomRepository
import com.binar.kos.utils.Resource
import kotlinx.coroutines.Dispatchers
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

class RoomViewModel(private val repository: RoomRepository) : ViewModel() {
    fun getRoomDetail(
        id: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getDetailRoom(id)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun addRoom(
        token: String,
        request: RequestBody,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.addRoom(token, request)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun bookingPost(
        token: String,
        idRoom: Int,
        totalUser: String,
        roomCost: String,
        typeCost: String,
        totalCost: String,
        userId: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.postBook(
                token,
                idRoom,
                totalUser,
                roomCost,
                typeCost,
                totalCost,
                userId
            )))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun bookingPut(
        token: String,
        idBook: Int,
        withCouple: String,
        withChildren: String,
        checkDocument: String,
        note: String,
        rentalDate: String,
        roomCost: String,
        totalCost: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.putBook(
                token,
                idBook,
                withCouple,
                withChildren,
                checkDocument,
                note,
                rentalDate,
                roomCost,
                totalCost
            )))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun getPaymentMethod(
        token: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getPaymentMethod(token)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun createTransaction(
        token: String,
        idBook: Int,
        idPayment: Int,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.putTransaction(token, idBook, idPayment)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun uploadTransactionFile(
        token: String,
        idBook: Int,
        request: RequestBody,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.putTransactionFile(token, idBook, request)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun cancelTransaction(
        token: String,
        idBook: Int,
        desc: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.cancelTransaction(token, idBook, desc)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun approveBook(
        token: String,
        idBook: Int,
        isApprove: String, status: String, description: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.approveBook(token, idBook, isApprove, status, description)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }

    fun getMyRoom(
        accessToken: String,
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = repository.getMyRoom(accessToken)))
        } catch (e: IOException) {
            emit(Resource.error(null, e.message ?: "Error Occurred!"))
        } catch (e: HttpException) {
            val jsonObj = JSONObject(e.response()?.errorBody()?.charStream()?.readText()!!)
            emit(Resource.error(null, jsonObj.getString("message") ?: "Error Occurred!"))
        }
    }
}