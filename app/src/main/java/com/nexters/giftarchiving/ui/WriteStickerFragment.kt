package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteStickerBinding
import com.nexters.giftarchiving.ui.recyclerview.adapter.WriteStickerMenuAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerFragment(
    override val viewModel: WriteViewModel
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteStickerBinding>() {
    override val layoutId = R.layout.fragment_write_sticker

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.stickerRv.adapter = WriteStickerMenuAdapter(viewModel)
    }
}