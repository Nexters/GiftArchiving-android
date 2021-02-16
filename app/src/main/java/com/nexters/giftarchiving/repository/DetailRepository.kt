package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.service.network.GiftZipService

internal class DetailRepository(private val giftZipService: GiftZipService) {

    @WorkerThread
    suspend fun getGift(giftId: String) = giftZipService.getGift(giftId)

}