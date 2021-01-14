package com.nexters.giftarchiving.util.color

import androidx.annotation.ColorRes
import com.nexters.giftarchiving.R

enum class BackgroundColor(@ColorRes val resId: Int) {
    RED(R.color.colorRed),
    GREEN(R.color.colorGreen),
    BLUE(R.color.colorBlue),
    WHITE(R.color.colorWhite)
}