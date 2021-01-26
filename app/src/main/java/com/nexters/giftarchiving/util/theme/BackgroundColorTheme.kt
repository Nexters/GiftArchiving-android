package com.nexters.giftarchiving.util.theme

import androidx.annotation.ColorRes
import com.nexters.giftarchiving.R

enum class BackgroundColorTheme(@ColorRes val backgroundColor: Int, @ColorRes val fontColor: Int) {
    MONO(R.color.colorMono, R.color.colorWhite),
    BLUE(R.color.colorBlue, R.color.colorWhite),
    ORANGE(R.color.colorOrange, R.color.colorWhite),
    YELLOW(R.color.colorYellow, R.color.colorDarkGray)
}