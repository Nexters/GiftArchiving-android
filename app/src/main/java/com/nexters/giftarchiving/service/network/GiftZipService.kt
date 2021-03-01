package com.nexters.giftarchiving.service.network

import com.nexters.giftarchiving.model.*
import com.nexters.giftarchiving.model.LoginResponse
import com.nexters.giftarchiving.model.User
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

internal interface GiftZipService {
    @POST("/api/user/signIn")
    suspend fun signIn(
        @Body user: User
    ): Response<LoginResponse>

    @Multipart
    @POST("/api/gift/create")
    suspend fun createGift(
        @Query("createdBy") createdBy: String,
        @Query("isReceiveGift") isReceiveGift: Boolean,
        @Query("name") name: String,
        @Query("content") content: String,
        @Query("receiveDate") receiveDate: String,
        @Query("category") category: String,
        @Query("reason") reason: String,
        @Query("emotion") emotion: String,
        @Query("frameType") frameType: String,
        @Query("bgColor") bgColor: String,
        @Part noBgImg: MultipartBody.Part,
        @Part bgImg: MultipartBody.Part
    ): Response<WriteResponse>

    @Multipart
    @POST("/api/gift/testImg")
    suspend fun testImg(
        @Part img: MultipartBody.Part
    ): String

    @GET("/api/gift/user/{createdBy}")
    suspend fun getGiftListAll(
        @Path("createdBy") createdBy: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("isReceiveGift") isReceiveGift: Boolean
    ): Response<GiftListResponse>

    @GET("/api/gift/{createdBy}/tag")
    suspend fun getGiftListByCategory(
        @Path("createdBy") createdBy: String,
        @Query("category") category: String?,
        @Query("size") size: Int?
    ): Response<GiftListResponse>

    @GET("/api/gift/{createdBy}/tag")
    suspend fun getGiftListByReason(
        @Path("createdBy") createdBy: String,
        @Query("reason") reason: String?,
        @Query("size") size: Int?
    ): Response<GiftListResponse>

    @GET("/api/gift/{createdBy}/tag")
    suspend fun getGiftListByName(
        @Path("createdBy") createdBy: String,
        @Query("name") name: String?,
        @Query("category") category: String?,
        @Query("reason") reason: String?,
        @Query("size") size: Int?
    ): Response<GiftListResponse>

    @GET("/api/gift/{createdBy}/tag")
    suspend fun getGiftListByContent(
        @Path("createdBy") createdBy: String,
        @Query("content") content: String?,
        @Query("category") category: String?,
        @Query("reason") reason: String?,
        @Query("size") size: Int?
    ): Response<GiftListResponse>

    @GET("/api/gift/{giftId}")
    suspend fun getGift(
        @Path("giftId") giftId: String
    ): Response<GiftDetailResponse>

    @GET("/api/admin")
    suspend fun getNoticeList(): Response<NoticeListResponse>

    @PUT("/api/gift/{giftId}")
    suspend fun updateGift(
        @Path("giftId") giftId: String,
        @Body request: GiftUpdate
    ): Response<Unit>

    @DELETE("/api/gift/{giftId}")
    suspend fun deleteGift(
        @Path("giftId") giftId: String
    ): Response<Unit>
}