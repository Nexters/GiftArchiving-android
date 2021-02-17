package com.nexters.giftarchiving.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GiftListResponse(
    @SerializedName("gifts")
    val giftListGifts : List<GiftResponse>,
    @SerializedName("page")
    val giftListPage : Int,
    @SerializedName("size")
    val giftListSize : Int,
    @SerializedName("totalCount")
    val giftListTotalCount : Int
): Parcelable
