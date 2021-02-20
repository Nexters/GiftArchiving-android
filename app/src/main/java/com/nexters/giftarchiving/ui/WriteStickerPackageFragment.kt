package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteStickerPackageBinding
import com.nexters.giftarchiving.ui.data.write.WritePackageSticker
import com.nexters.giftarchiving.ui.data.write.WriteSticker
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteStickerPackageFragment(
    override val viewModel: WriteViewModel
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteStickerPackageBinding>() {
    override val layoutId = R.layout.fragment_write_sticker_package

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replacePackageStickerView(WriteSticker.PACKAGE, WritePackageSticker.HEART)
    }

    private fun replacePackageStickerView(stickerType: WriteSticker, packageType: WritePackageSticker) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val fragment = when (stickerType) {
            WriteSticker.SINGLE -> WriteStickerPackageSingleFragment(viewModel, packageType)
            WriteSticker.PACKAGE -> WriteStickerPackagePreviewFragment(viewModel) { st, pst ->
                replacePackageStickerView(st, pst)
            }
        }
        fragmentTransaction.replace(R.id.frame_layout, fragment).commit()
    }
}