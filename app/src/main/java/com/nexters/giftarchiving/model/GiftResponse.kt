package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

internal data class GiftResponse(
    @SerializedName("id")
    val giftId : String,
    @SerializedName("name")
    val giftName : String,
    @SerializedName("imgUrl")
    val giftImgUrl : String,
    @SerializedName("content")
    val giftContent : String
)