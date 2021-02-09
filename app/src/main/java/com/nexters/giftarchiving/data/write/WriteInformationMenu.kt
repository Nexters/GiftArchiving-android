package com.nexters.giftarchiving.data.write

import androidx.annotation.DrawableRes
import com.nexters.giftarchiving.R

internal data class WriteInformationMenu(
    val title: String,
    @DrawableRes val darkIcon: Int,
    @DrawableRes val lightIcon: Int
) {
    companion object {
        @JvmStatic
        fun getEmptyCategory() = WriteInformationMenu(
            "카테고리",
            R.drawable.ic_oval_copy_10_b,
            R.drawable.ic_oval_copy_10
        )

        @JvmStatic
        fun getEmptyPurpose() = WriteInformationMenu(
            "목적",
            R.drawable.ic_icon_present_b,
            R.drawable.ic_icon_present
        )

        @JvmStatic
        fun getEmptyEmotion() = WriteInformationMenu(
            "감정",
            R.drawable.ic_icon_feeling_b,
            R.drawable.ic_icon_feeling
        )
    }
}