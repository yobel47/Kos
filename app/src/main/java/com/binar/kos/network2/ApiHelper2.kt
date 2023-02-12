package com.binar.kos.network2

import com.binar.kos.data.remote.request.*
import com.binar.kos.network2.ApiService2
import com.binar.kos.utils.getHeaderMap
import okhttp3.RequestBody

class ApiHelper2(private val apiService2: ApiService2) {
    suspend fun getSearch(params: String) = apiService2.fetchSearch(params)
    suspend fun getAllRooms() = apiService2.getAllRooms()
    suspend fun getDetailRoom(id: String) = apiService2.fetchDetailRoom(id)
    suspend fun postRoom(accessToken: String, request: RequestBody) = apiService2.postRoom(
        getHeaderMap(accessToken), request)

    suspend fun postBook(
        accessToken: String, idRoom: Int, totalUser: String, roomCost: String,
        typeCost: String, totalCost: String, userId: String,
    ) = apiService2.postBook(
            getHeaderMap(accessToken),
            idRoom, totalUser, roomCost, typeCost, totalCost, userId)

    suspend fun putBook(
        accessToken: String, idBook: Int, withCouple: String,
        withChildren: String, checkDocument: String, note: String,
        rentalDate: String, roomCost: String, totalCost: String,
    ) = apiService2.putBook(
            getHeaderMap(accessToken),
            idBook, withCouple, withChildren, checkDocument,
            note, rentalDate, roomCost, totalCost)

    suspend fun getPaymentMethod(accessToken: String) = apiService2.getPayment(getHeaderMap(accessToken))

    suspend fun putTransaction(accessToken: String, idBook: Int, paymentId: Int) =
        apiService2.putTransaction(getHeaderMap(accessToken), idBook, TransactionRequest(paymentId))

    suspend fun putTransactionFile(accessToken: String, idBook: Int, request: RequestBody) =
        apiService2.putTransactionFile(getHeaderMap(accessToken), idBook, request)

    suspend fun cancelTransaction(accessToken: String, idBook: Int, desc: String) =
        apiService2.cancelTransaction(getHeaderMap(accessToken), idBook, CancelTransactionRequest(desc))
}