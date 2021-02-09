package com.nexters.giftarchiving.ui.viewpager.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nexters.giftarchiving.ui.WriteStickerFragment
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class StickerSlidePagerAdapter(
    fa: FragmentActivity,
    private val viewModel: WriteViewModel
) : FragmentStateAdapter(fa) {
    override fun getItemCount() = STICKER_TYPE_COUNT
    override fun createFragment(position: Int) = WriteStickerFragment(viewModel, position)

    companion object {
        private const val STICKER_TYPE_COUNT = 2
    }
}