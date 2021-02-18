package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

internal data class GiftUpdate(
    @SerializedName("content")
    val content: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("receiveDate")
    val receiveDate: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("emotion")
    val emotion: String,
    @SerializedName("reason")
    val reason: String
)