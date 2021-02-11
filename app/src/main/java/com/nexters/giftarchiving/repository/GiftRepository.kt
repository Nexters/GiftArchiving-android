package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.service.network.GiftZipService
import retrofit2.http.Path
import retrofit2.http.Query

internal class GiftRepository(private val giftZipService: GiftZipService) {

    @WorkerThread
    suspend fun getGiftListAll(createdBy: String, page : Int, size : Int, isReceiveGift: Boolean) = giftZipService.getGiftListAll(createdBy, page, size, isReceiveGift)

    @WorkerThread
    suspend fun getGiftListByTag(createdBy: String, category: String?, emotion: String?, reason: String?, name: String?, content: String?, page : Int, size : Int) =
        giftZipService.getGiftListByTag(createdBy, category, emotion, reason, name, content, page, size)
}