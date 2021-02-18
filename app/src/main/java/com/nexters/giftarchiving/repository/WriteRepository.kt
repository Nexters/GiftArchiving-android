package com.nexters.giftarchiving.repository

import androidx.annotation.WorkerThread
import com.nexters.giftarchiving.model.GiftUpdate
import com.nexters.giftarchiving.model.WriteResponse
import com.nexters.giftarchiving.service.network.GiftZipService
import com.nexters.giftarchiving.util.DateConvert
import okhttp3.MultipartBody
import java.time.LocalDate

internal class WriteRepository(private val giftZipService: GiftZipService) {

    @WorkerThread
    suspend fun createGift(
        createdBy: String,
        isReceiveGift: Boolean,
        name: String,
        content: String,
        receiveDate: LocalDate,
        category: String,
        reason: String,
        emotion: String,
        frameType: String,
        bgColor: String,
        noBgImg: MultipartBody.Part,
        bgImg: MultipartBody.Part
    ): WriteResponse {
        val date = DateConvert.localDateToLocalDateTimeStr(receiveDate)
        return giftZipService.createGift(
            createdBy,
            isReceiveGift,
            name,
            content,
            date,
            category,
            reason,
            emotion,
            frameType,
            bgColor,
            noBgImg,
            bgImg
        )
    }

    @WorkerThread
    suspend fun updateGift(giftId: String, gift: GiftUpdate) =
        giftZipService.updateGift(giftId, gift)
}