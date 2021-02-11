package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.model.User
import com.nexters.giftarchiving.service.network.GiftZipService

internal class LoginRepository(private val giftZipService: GiftZipService) {

    @WorkerThread
    suspend fun signIn(userId: String) = giftZipService.signIn(User(userId))
}