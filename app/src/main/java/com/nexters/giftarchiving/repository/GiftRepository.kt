package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.service.network.GiftZipService
import retrofit2.http.Path
import retrofit2.http.Query

internal class GiftRepository(private val giftZipService: GiftZipService) {

    @WorkerThread
    suspend fun getGiftListAll(createdBy: String, page : Int, size : Int, isReceiveGift: Boolean) = giftZipService.getGiftListAll(createdBy, page, size, isReceiveGift)

    @WorkerThread
    suspend fun getGiftListByCategory(createdBy: String, category: String?, size : Int?) =
        giftZipService.getGiftListByCategory(createdBy, category, size)

    @WorkerThread
    suspend fun getGiftListByReason(createdBy: String, reason: String?, size : Int?) =
        giftZipService.getGiftListByReason(createdBy, reason, size)

    @WorkerThread
    suspend fun getGiftListByName(createdBy: String, name: String?, size : Int?) =
        giftZipService.getGiftListByName(createdBy, name, size)

    @WorkerThread
    suspend fun getGiftListByContent(createdBy: String, content: String?, size : Int?) =
        giftZipService.getGiftListByContent(createdBy, content, size)
}