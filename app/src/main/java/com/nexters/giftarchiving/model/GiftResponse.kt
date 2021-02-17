package com.nexters.giftarchiving.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Url
import java.time.LocalDate

@Parcelize
data class GiftResponse(
    @SerializedName("id")
    val giftId : String,
    @SerializedName("name")
    val giftName : String,
    @SerializedName("imgUrl")
    val giftImgUrl : String,
    @SerializedName("content")
    val giftContent : String,
    @SerializedName("category")
    val giftCategory : String,
    @SerializedName("emotion")
    val giftEmotion : String,
    @SerializedName("reason")
    val giftReason : String,
    @SerializedName("receiveDate")
    val giftReceiveDate : String,
    @SerializedName("bgColor")
    val bgColor : String,
    @SerializedName("isReceiveGift")
    val isReceiveGift : Boolean
): Parcelable