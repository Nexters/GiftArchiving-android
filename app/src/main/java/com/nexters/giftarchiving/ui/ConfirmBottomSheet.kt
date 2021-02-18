package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseBottomSheetFragment
import com.nexters.giftarchiving.base.BaseConfirmDialogListener
import com.nexters.giftarchiving.databinding.FragmentBottomsheetConfirmBinding
import com.nexters.giftarchiving.viewmodel.ConfirmBottomSheetViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ConfirmBottomSheet(
    val title: String = "",
    val subTitle: String = "",
    val confirmTitle: String = "확인",
    val cancelTitle: String = "취소",
    val listener: BaseConfirmDialogListener = BaseConfirmDialogListener()
) : BaseBottomSheetFragment<ConfirmBottomSheetViewModel, FragmentBottomsheetConfirmBinding>() {
    override val layoutId = R.layout.fragment_bottomsheet_confirm
    override val viewModel: ConfirmBottomSheetViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProperties()
    }

    private fun setProperties() {
        with(binding) {
            titleTv.text = title
            subTitleTv.text = subTitle
            confirmBtn.run {
                text = confirmTitle
                setOnClickListener {
                    listener.onConfirm()
                    dismiss()
                }
            }
            cancelBtn.run {
                text = cancelTitle
                setOnClickListener {
                    listener.onCancel()
                    dismiss()
                }
            }
        }
    }
}