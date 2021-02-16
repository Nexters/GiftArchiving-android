package com.nexters.giftarchiving.ui

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteInformationMenuBinding
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.ui.recyclerview.adapter.WriteInformationMenuAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteInformationMenuFragment(
    override val viewModel: WriteViewModel,
    private val menuType: WriteMenu,
    private val isReceive: Boolean,
    private val position: Int
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteInformationMenuBinding>() {
    override val layoutId = R.layout.fragment_write_information_menu

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.menuRv.apply {
            adapter = WriteInformationMenuAdapter(viewModel, menuType, isReceive, position)
            layoutManager = object : GridLayoutManager(requireContext(), MENU_SPAN_COUNT) {
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams?): Boolean {
                    lp?.height = height.div(MENU_ROW_COUNT)
                    return true
                }
            }
            setHasFixedSize(true)
        }
    }

    companion object {
        private const val MENU_SPAN_COUNT = 4
        private const val MENU_ROW_COUNT = 2
    }
}