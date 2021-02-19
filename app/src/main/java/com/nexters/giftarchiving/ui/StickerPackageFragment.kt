package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentStickerPackageBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.ui.data.write.WriteSticker
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class StickerPackageFragment(
    override val viewModel: WriteViewModel
) : BaseViewPagerFragment<WriteViewModel, FragmentStickerPackageBinding>() {
    override val layoutId = R.layout.fragment_sticker_package

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replacePackageStickerView(WriteSticker.PACKAGE, WritePackageSticker.HEART)
    }

    private fun replacePackageStickerView(stickerType: WriteSticker, packageType: WritePackageSticker) {
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = when (stickerType) {
            WriteSticker.PACKAGE -> WriteStickerPackageFragment(viewModel) { st, pst ->
                replacePackageStickerView(st, pst)
            }
            WriteSticker.SINGLE -> WriteStickerPackageDetailFragment(viewModel, packageType)
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment).commit()
    }
}