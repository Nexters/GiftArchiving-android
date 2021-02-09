package com.nexters.giftarchiving.model

import com.google.gson.annotations.SerializedName

internal data class LoginResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
) {
    fun isSuccess() = (message == SUCCESS)

    companion object {
        private const val SUCCESS = "SUCCESS"
    }
}