package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSearchBinding
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {
    override val layoutId = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}