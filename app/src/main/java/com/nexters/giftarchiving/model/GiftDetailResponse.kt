package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

internal data class GiftDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("createdBy")
    val createdBy: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("isReceiveGift")
    val isReceiveGift: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("receiveDate")
    val receiveDate: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("bgImgUrl")
    val bgImgUrl: String,
    @SerializedName("noBgImgUrl")
    val noBgImgUrl: String,
    @SerializedName("bgColor")
    val bgColor: String,
    @SerializedName("frameType")
    val frameType: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("emotion")
    val emotion: String,
    @SerializedName("reason")
    val reason: String
)