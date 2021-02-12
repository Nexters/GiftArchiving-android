package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

internal data class GiftResponse(
    @SerializedName("id")
    val giftId : String,
    @SerializedName("name")
    val giftName : String,
    @SerializedName("imgUrl")
    val giftImgUrl : String,
    @SerializedName("content")
    val giftContent : String,
    @SerializedName("categoty")
    val giftCategory : String,
    @SerializedName("emotion")
    val giftEmotion : String,
    @SerializedName("reason")
    val giftReason : String,
    @SerializedName("receiveDate")
    val giftReceiveDate : LocalDate
)