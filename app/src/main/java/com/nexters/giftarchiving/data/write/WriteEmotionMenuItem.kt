package com.nexters.giftarchiving.data.write

internal data class WriteEmotionMenuItem(
    val emotionMenu: WriteEmotionMenu
) : WriteInformationMenuItem(emotionMenu)