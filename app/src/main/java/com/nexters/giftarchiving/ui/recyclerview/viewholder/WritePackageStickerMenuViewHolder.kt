package com.nexters.giftarchiving.ui.recyclerview.viewholder

import com.nexters.giftarchiving.base.BaseViewHolder
import com.nexters.giftarchiving.data.write.WritePackageStickerMenuItem
import com.nexters.giftarchiving.databinding.ItemWriteStickerPackageBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker

internal class WritePackageStickerMenuViewHolder(
    private val binding: ItemWriteStickerPackageBinding,
    private val onClickCallBack: (WritePackageSticker) -> Unit = {}
) : BaseViewHolder<WritePackageStickerMenuItem>(binding) {
    override fun bind(pos: Int, item: WritePackageStickerMenuItem) {
        super.bind(pos, item)
        with(binding) {
            iv.setOnClickListener { onClickCallBack(item.packageType) }
        }
    }
}