package com.binar.kos.network2

import com.binar.kos.data.remote.request.*
import com.binar.kos.utils.getHeaderMap
import okhttp3.RequestBody

class ApiHelper2(private val apiService2: ApiService2) {
    suspend fun getSearch(
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
    ) = apiService2.fetchSearch(
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
        twf_hours_access
    )

    suspend fun getAllRooms() = apiService2.getAllRooms()
    suspend fun getPromoRooms() = apiService2.getPromoRooms()
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

    suspend fun getPaymentMethod(accessToken: String) =
        apiService2.getPayment(getHeaderMap(accessToken))

    suspend fun putTransaction(accessToken: String, idBook: Int, paymentId: Int) =
        apiService2.putTransaction(getHeaderMap(accessToken), idBook, TransactionRequest(paymentId))

    suspend fun putTransactionFile(accessToken: String, idBook: Int, request: RequestBody) =
        apiService2.putTransactionFile(getHeaderMap(accessToken), idBook, request)

    suspend fun cancelTransaction(accessToken: String, idBook: Int, desc: String) =
        apiService2.cancelTransaction(getHeaderMap(accessToken),
            idBook,
            CancelTransactionRequest(desc))

    suspend fun subscribeToken(accessToken: String, firebaseToken: RequestBody) =
        apiService2.subscribeToken(getHeaderMap(accessToken), firebaseToken)

    suspend fun getNotification(accessToken: String, category: String) =
        apiService2.getNotif(getHeaderMap(accessToken), category)

    suspend fun getHistory(accessToken: String, status: String?) =
        apiService2.getHistory(getHeaderMap(accessToken), status)

    suspend fun getHistoryPenyewa(accessToken: String, status: String?) =
        apiService2.getHistoryPenyewa(getHeaderMap(accessToken), status)

    suspend fun approveBook(accessToken: String, idBook: Int, isApprove: String, status: String, description: String) =
        apiService2.putApprovalBooking(getHeaderMap(accessToken), idBook, ApprovalRequest(isApprove, status, description))

    suspend fun uploadProfile(accessToken: String, request: RequestBody) = apiService2.uploadProfilePicture(
        getHeaderMap(accessToken), request)
}