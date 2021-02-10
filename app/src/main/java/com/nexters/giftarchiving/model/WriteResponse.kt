package com.nexters.giftarchiving.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WriteResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("noBgImg")
    val noBgImgUrl: String,
    @SerializedName("bgImg")
    val bgImgUrl: String
) : Parcelable