package com.binar.kos.network2

import com.binar.kos.data.local.entity.Kos
import com.binar.kos.data.remote.request.CancelTransactionRequest
import com.binar.kos.data.remote.request.LoginRequest
import com.binar.kos.data.remote.request.TransactionRequest
import com.binar.kos.data.remote.response.TransactionResponse
import com.binar.kos.data.remote.response.addRoom.AddRoomResponse
import com.binar.kos.data.remote.response.bookingRoom.post.BookingPostResponse
import com.binar.kos.data.remote.response.bookingRoom.put.BookingPutResponse
import com.binar.kos.data.remote.response.paymentMethod.PaymentMethodResponseItem
import com.binar.kos.data.remote.response.roomResponse.RoomResponse
import com.binar.kos.data.remote.response.searchResponse.SearchResponse
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiService2 {
    @GET("search")
    suspend fun fetchSearch(
        @Query("keyword") params: String,
    ): List<SearchResponse>

    @GET("/api/room")
    suspend fun getAllRooms() : ArrayList<Kos>

    @GET("detail")
    suspend fun fetchDetailRoom(
        @Query("id") params: String,
    ): RoomResponse

    @POST("/api/room")
    suspend fun postRoom(
        @HeaderMap header: Map<String, String>,
        @Body request: RequestBody
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
        @Body request: RequestBody
    ): TransactionResponse

    @PUT("transaction/cancel/{id}")
    suspend fun cancelTransaction(
        @HeaderMap header: Map<String, String>,
        @Path("id") id_book: Int,
        @Body request: CancelTransactionRequest,
    ): TransactionResponse
}
