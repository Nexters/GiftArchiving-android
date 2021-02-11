package com.nexters.giftarchiving.ui

import android.os.Bundle
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseViewPagerFragment
import com.nexters.giftarchiving.databinding.FragmentWriteInformationMenuBinding
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.ui.recyclerview.adapter.WriteInformationMenuAdapter
import com.nexters.giftarchiving.viewmodel.WriteViewModel

internal class WriteInformationMenuFragment(
    override val viewModel: WriteViewModel,
    private val menuType: WriteMenu
) : BaseViewPagerFragment<WriteViewModel, FragmentWriteInformationMenuBinding>() {
    override val layoutId = R.layout.fragment_write_information_menu

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.menuRv.adapter = WriteInformationMenuAdapter(viewModel, menuType)
    }
}