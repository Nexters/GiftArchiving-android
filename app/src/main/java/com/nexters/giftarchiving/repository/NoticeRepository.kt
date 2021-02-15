package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.service.network.GiftZipService

internal class NoticeRepository(private val giftZipService: GiftZipService) {
    @WorkerThread
    suspend fun getNoticeList() = giftZipService.getNoticeList()
}