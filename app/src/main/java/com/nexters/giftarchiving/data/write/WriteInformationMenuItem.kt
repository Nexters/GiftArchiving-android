package com.nexters.giftarchiving.data.write

import com.nexters.giftarchiving.base.BaseItem

internal abstract class WriteInformationMenuItem(
    val menu: WriteInformationMenu,
    override val itemId: String = menu.title
) : BaseItem