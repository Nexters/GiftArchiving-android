package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.model.GiftUpdate
import com.nexters.giftarchiving.service.network.GiftZipService

internal class DetailRepository(private val giftZipService: GiftZipService) {
    @WorkerThread
    suspend fun getGift(giftId: String) = giftZipService.getGift(giftId)

    @WorkerThread
    suspend fun updateGift(giftId: String, gift: GiftUpdate) =
        giftZipService.updateGift(giftId, gift)

    @WorkerThread
    suspend fun deleteGift(giftId: String) = giftZipService.deleteGift(giftId)
}