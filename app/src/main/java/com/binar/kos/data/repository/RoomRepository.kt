package com.binar.kos.data.repository

import com.binar.kos.network2.ApiHelper2
import okhttp3.RequestBody

class RoomRepository(private val apiHelper2: ApiHelper2) {
    suspend fun getAllRooms() = apiHelper2.getAllRooms()
    suspend fun getPromoRooms() = apiHelper2.getPromoRooms()
    suspend fun getDetailRoom(id: String) = apiHelper2.getDetailRoom(id)
    suspend fun addRoom(accessToken: String, request: RequestBody) =
        apiHelper2.postRoom(accessToken, request)

    suspend fun postBook(
        accessToken: String, idRoom: Int, totalUser: String, roomCost: String,
        typeCost: String, totalCost: String, userId: String,
    ) =
        apiHelper2.postBook(accessToken, idRoom, totalUser, roomCost, typeCost, totalCost, userId)

    suspend fun putBook(
        accessToken: String, idBook: Int, withCouple: String,
        withChildren: String, checkDocument: String, note: String,
        rentalDate: String, roomCost: String, totalCost: String,
    ) =
        apiHelper2.putBook(accessToken,
            idBook, withCouple, withChildren, checkDocument,
            note, rentalDate, roomCost, totalCost)

    suspend fun getPaymentMethod(accessToken: String) = apiHelper2.getPaymentMethod(accessToken)

    suspend fun putTransaction(
        accessToken: String, idBook: Int, idPayment: Int
    ) = apiHelper2.putTransaction(accessToken,
            idBook, idPayment)

    suspend fun putTransactionFile(
        accessToken: String, idBook: Int, request: RequestBody
    ) = apiHelper2.putTransactionFile(accessToken,
            idBook, request)

    suspend fun cancelTransaction(
        accessToken: String, idBook: Int, desc: String
    ) = apiHelper2.cancelTransaction(accessToken,
            idBook, desc)

    suspend fun approveBook(
        accessToken: String, idBook: Int, isApprove: String, status: String, description: String
    ) = apiHelper2.approveBook(accessToken, idBook,
        isApprove, status, description)


    suspend fun getMyRoom(accessToken: String) = apiHelper2.getMyRoom(accessToken)

}