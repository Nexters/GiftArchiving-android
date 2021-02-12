package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

data class GiftListResponse(
    @SerializedName("gifts")
    val giftListGifts : List<GiftResponse>,
    @SerializedName("page")
    val giftListPage : Int,
    @SerializedName("size")
    val giftListSize : Int,
    @SerializedName("totalCount")
    val giftListTotalCount : Int
)
