package com.nexters.giftarchiving.ui.data

import androidx.annotation.ColorRes
import com.nexters.giftarchiving.R

enum class BackgroundColorTheme(
    val iosName: String,
    @ColorRes val backgroundColor: Int,
    @ColorRes val popupBackgroundColor: Int,
    val isDarkMode: Boolean
) {
    MONO("charcoalGrey", R.color.colorMono, R.color.colorPopupMono, true),
    BLUE("ceruleanBlue", R.color.colorBlue, R.color.colorPopupBlue, true),
    ORANGE("pinkishOrange", R.color.colorOrange, R.color.colorPopupOrange, true),
    YELLOW("wheat", R.color.colorYellow, R.color.colorPopupYellow, false)
}