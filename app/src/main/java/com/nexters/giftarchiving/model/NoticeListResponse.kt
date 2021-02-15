package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

data class NoticeListResponse(
    @SerializedName("noticeList")
    val noticeList : List<NoticeResponse>
)