package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

internal data class WriteResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("noBgImg")
    val noBgImgUrl: String,
    @SerializedName("bgImg")
    val bgImgUrl: String
)