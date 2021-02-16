package com.nexters.giftarchiving.ui.data.write

internal enum class WriteMenu(val isShowBottomBar: Boolean = true) {
    DATE(false),
    INFORMATION_CATEGORY(false),
    INFORMATION_PURPOSE(false),
    INFORMATION_EMOTION(false),
    FRAME(true),
    STICKER(true),
    BACKGROUND_COLOR(false)
}