package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSettingsBinding
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SettingsFragment : BaseFragment<SettingsViewModel, FragmentSettingsBinding>() {
    override val layoutId = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}