package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentWriteBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.WriteViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class WriteFragment : BaseFragment<WriteViewModel, FragmentWriteBinding>() {
    override val layoutId = R.layout.fragment_write
    override val viewModel: WriteViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(viewModel.stickerResources) {
            if (it.isNotEmpty()) {
                binding.imageLayout.addView(
                    ImageView(requireContext()).apply {
                        val linearLayoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                        ).apply { gravity = Gravity.CENTER }

                        setImageResource(it.last())
                        layoutParams = linearLayoutParams
                    }
                )
            }
        }
    }
}