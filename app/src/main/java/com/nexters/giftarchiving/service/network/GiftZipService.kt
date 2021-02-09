package com.nexters.giftarchiving.service.network

import com.nexters.giftarchiving.model.LoginResponse
import com.nexters.giftarchiving.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface GiftZipService {
    @POST("/api/user/signIn")
    suspend fun signIn(
        @Body user: User
    ): LoginResponse
}