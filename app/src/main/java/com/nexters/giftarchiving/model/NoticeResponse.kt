package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class NoticeResponse(
    @SerializedName("title")
    val title : String,
    @SerializedName("content")
    val content : String,
    @SerializedName("createdAt")
    val createdAt : String
)