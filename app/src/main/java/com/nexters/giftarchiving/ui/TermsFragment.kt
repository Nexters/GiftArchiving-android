package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentTermsBinding
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class TermsFragment : BaseFragment<SettingsViewModel, FragmentTermsBinding>() {
    override val layoutId = R.layout.fragment_terms
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}