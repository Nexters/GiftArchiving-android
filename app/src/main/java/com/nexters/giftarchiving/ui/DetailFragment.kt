package com.nexters.giftarchiving.ui

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseConfirmDialogListener
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentDetailBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.DetailViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class DetailFragment : BaseFragment<DetailViewModel, FragmentDetailBinding>() {
    override val layoutId = R.layout.fragment_detail
    override val viewModel: DetailViewModel by viewModel()
    override val navArgs by navArgs<DetailFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.clickMore) { showMenuDialog() }
        observe(viewModel.clickDelete) { showDeleteDialog() }
    }

    private fun showMenuDialog() {
        DetailMoreBottomSheet(viewModel)
            .show(parentFragmentManager, CONFIRM_DIALOG_TAG)
    }

    private fun showDeleteDialog() {
        val listener = object : BaseConfirmDialogListener() {
            override fun onConfirm() {
                super.onConfirm()
                viewModel.deleteGift()
            }
        }
        ConfirmBottomSheet(
            title = "해당 선물을 삭제하시겠습니까?",
            subTitle = "기록을 삭제하면 재작성이 불가합니다.",
            confirmTitle = "삭제",
            cancelTitle = "취소",
            listener = listener
        ).show(parentFragmentManager, DELETE_DIALOG_TAG)
    }

    companion object {
        private const val CONFIRM_DIALOG_TAG = "confirm dialog"
        private const val DELETE_DIALOG_TAG = "delete dialog"
    }
}