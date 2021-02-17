package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseBottomSheetFragment
import com.nexters.giftarchiving.databinding.FragmentBottomsheetDetailMoreBinding
import com.nexters.giftarchiving.viewmodel.DetailViewModel

internal class DetailMoreBottomSheet(
    override val viewModel: DetailViewModel
) : BaseBottomSheetFragment<DetailViewModel, FragmentBottomsheetDetailMoreBinding>() {
    override val layoutId = R.layout.fragment_bottomsheet_detail_more

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProperties()
    }

    private fun setProperties() {
        with(binding) {
            editLayout.setOnClickListener {
                viewModel.onClickEdit()
                dismiss()
            }
            deleteLayout.setOnClickListener {
                viewModel.onClickDelete()
                dismiss()
            }
            shareLayout.setOnClickListener {
                viewModel.onClickShare()
                dismiss()
            }
            cancelBtn.setOnClickListener { dismiss() }
        }
    }
}