package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentListBinding
import com.nexters.giftarchiving.viewmodel.ListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    override val layoutId = R.layout.fragment_list
    override val viewModel: ListViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}