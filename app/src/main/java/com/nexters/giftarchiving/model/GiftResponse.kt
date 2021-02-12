package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Url
import java.time.LocalDate

data class GiftResponse(
    @SerializedName("id")
    val giftId : String,
    @SerializedName("name")
    val giftName : String,
    @SerializedName("imgUrl")
    val giftImgUrl : Url,
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