package com.binar.kos.network2

import com.binar.kos.data.local.entity.Kos
import com.binar.kos.data.remote.request.ApprovalRequest
import com.binar.kos.data.remote.request.CancelTransactionRequest
import com.binar.kos.data.remote.request.TransactionRequest
import com.binar.kos.data.remote.response.ApproveBookResponse
import com.binar.kos.data.remote.response.NotificationResponse
import com.binar.kos.data.remote.response.TransactionResponse
import com.binar.kos.data.remote.response.addRoom.AddRoomResponse
import com.binar.kos.data.remote.response.bookingRoom.post.BookingPostResponse
import com.binar.kos.data.remote.response.bookingRoom.put.BookingPutResponse
import com.binar.kos.data.remote.response.historyPenyewa.HistoryPenyewaResponse
import com.binar.kos.data.remote.response.historyResponse.HistoryResponse
import com.binar.kos.data.remote.response.myRoom.MyRoomResponse
import com.binar.kos.data.remote.response.paymentMethod.PaymentMethodResponseItem
import com.binar.kos.data.remote.response.roomResponse.RoomResponse
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService2 {
    @GET("filter")
    suspend fun fetchSearch(
        @Query("search") search: String?,
        @Query("tipekostputra") tipekostputra: Int,
        @Query("tipekosputri") tipekosputri: Int,
        @Query("tipekoscampur") tipekoscampur: Int,
        @Query("priceday") priceday: Int,
        @Query("priceweek") priceweek: Int,
        @Query("pricemonth") pricemonth: Int,
        @Query("minprice") minprice: Int,
        @Query("maxprice") maxprice: Int,
        @Query("bed") bed: Int,
        @Query("fan") fan: Int,
        @Query("chair") chair: Int,
        @Query("table") table: Int,
        @Query("pillow") pillow: Int,
        @Query("window") window: Int,
        @Query("tvinroom") tvinroom: Int,
        @Query("wardrobe") wardrobe: Int,
        @Query("hot_water") hot_water: Int,
        @Query("seat_toilet") seat_toilet: Int,
        @Query("squat_toilet") squat_toilet: Int,
        @Query("inside_bathroom") inside_bathroom: Int,
        @Query("outside_bathroom") outside_bathroom: Int,
        @Query("air_conditioning") air_conditioning: Int,
        @Query("electricity") electricity: Int,
        @Query("iron") iron: Int,
        @Query("wifi") wifi: Int,
        @Query("kitchen") kitchen: Int,
        @Query("loundry") loundry: Int,
        @Query("mushola") mushola: Int,
        @Query("dispenser") dispenser: Int,
        @Query("car_parking") car_parking: Int,
        @Query("motorcycle_parking") motorcycle_parking: Int,
        @Query("bicycle_parking") bicycle_parking: Int,
        @Query("living_room") living_room: Int,
        @Query("tvinlivingroom") tvinlivingroom: Int,
        @Query("washing_machine") washing_machine: Int,
        @Query("refrigerator") refrigerator: Int,
        @Query("pet") pet: Int,
        @Query("couple") couple: Int,
        @Query("person") person: Int,
        @Query("check_in") check_in: Int,
        @Query("check_out") check_out: Int,
        @Query("children") children: Int,
        @Query("add_electricity") add_electricity: Int,
        @Query("smoking_in_room") smoking_in_room: Int,
        @Query("special_student") special_student: Int,
        @Query("special_employees") special_employees: Int,
        @Query("twf_hours_access") twf_hours_access: Int,
    ): List<SearchResponse>

    @GET("/api/room")
    suspend fun getAllRooms(): ArrayList<Kos>

    @GET("promo")
    suspend fun getPromoRooms(): ArrayList<Kos>

    @GET("detail")
    suspend fun fetchDetailRoom(
        @Query("id") params: String,
    ): RoomResponse

    @POST("/api/room")
    suspend fun postRoom(
        @HeaderMap header: Map<String, String>,
        @Body request: RequestBody,
    ): AddRoomResponse

    @FormUrlEncoded
    @POST("booking/{id}")
    suspend fun postBook(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_room: Int,
        @Field("total_user") total_user: String,
        @Field("room_cost") room_cost: String,
        @Field("type_cost") type_cost: String,
        @Field("total_cost") total_cost: String,
        @Field("user_id") user_id: String,
    ): BookingPostResponse

    @FormUrlEncoded
    @PUT("booking/{id}")
    suspend fun putBook(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_book: Int,
        @Field("with_couple") with_couple: String,
        @Field("with_children") with_children: String,
        @Field("check_document") check_document: String,
        @Field("note") note: String,
        @Field("rental_date") rental_date: String,
        @Field("room_cost") room_cost: String,
        @Field("total_cost") total_cost: String,
    ): BookingPutResponse

    @GET("/api/payment")
    suspend fun getPayment(
        @HeaderMap header: Map<String, String>,
    ): List<PaymentMethodResponseItem>

    @PUT("transaction/{id}")
    suspend fun putTransaction(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_book: Int,
        @Body request: TransactionRequest,
    ): TransactionResponse

    @PUT("transaction/upload/{id}")
    suspend fun putTransactionFile(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_book: Int,
        @Body request: RequestBody,
    ): TransactionResponse

    @PUT("transaction/cancel/{id}")
    suspend fun cancelTransaction(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_book: Int,
        @Body request: CancelTransactionRequest,
    ): TransactionResponse

    @POST("/api/notification")
    suspend fun subscribeToken(
        @HeaderMap header: Map<String, String>,
        @Body request: RequestBody,
    ): TransactionResponse

    @GET("/api/notification")
    suspend fun getNotif(
        @HeaderMap header: Map<String, String>,
        @Query("category") category: String?,
    ): List<NotificationResponse>

    @GET("transaction")
    suspend fun getHistory(
        @HeaderMap header: Map<String, String>,
        @Query("status") status: String?,
    ): List<HistoryResponse>

    @GET("booking")
    suspend fun getHistoryPenyewa(
        @HeaderMap header: Map<String, String>,
        @Query("status") status: String?,
    ): List<HistoryPenyewaResponse>

    @PUT("approval/booking/{id}")
    suspend fun putApprovalBooking(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_book: Int,
        @Body request: ApprovalRequest
    ): ApproveBookResponse


    @POST("/api/profile")
    suspend fun uploadProfilePicture(
        @HeaderMap header: Map<String, String>,
        @Body request: RequestBody,
    ): ApproveBookResponse

    @GET("/api/room/pemilik")
    suspend fun getMyRoom(
        @HeaderMap header: Map<String, String>,
    ): List<MyRoomResponse>


}
