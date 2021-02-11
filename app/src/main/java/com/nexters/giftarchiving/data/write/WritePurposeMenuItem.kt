package com.nexters.giftarchiving.data.write

internal data class WritePurposeMenuItem(
    val purposeMenu: WritePurposeMenu
) : WriteInformationMenuItem(purposeMenu)