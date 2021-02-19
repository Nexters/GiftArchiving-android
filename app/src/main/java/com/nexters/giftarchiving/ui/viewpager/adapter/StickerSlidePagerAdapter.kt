package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.ui.StickerPackageFragment
import com.nexters.giftarchiving.ui.WriteStickerFragment
import com.nexters.giftarchiving.ui.data.write.WriteSticker
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class StickerSlidePagerAdapter(
    fa: FragmentActivity,
    private val viewModel: WriteViewModel
) : FragmentStateAdapter(fa) {
    override fun getItemCount() = WriteSticker.values().size
    override fun createFragment(position: Int) =
        when(position) {
            WriteSticker.PACKAGE.ordinal -> StickerPackageFragment(viewModel)
            else -> WriteStickerFragment(viewModel)
        }
}