package com.nexters.giftarchiving.data.write

internal data class WriteCategoryMenuItem(
    val categoryMenu: WriteCategoryMenu
) : WriteInformationMenuItem(categoryMenu)