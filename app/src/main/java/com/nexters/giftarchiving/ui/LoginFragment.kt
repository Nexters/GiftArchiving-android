package com.nexters.giftarchiving.ui

import androidx.navigation.fragment.navArgs
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentLoginBinding
import com.nexters.giftarchiving.viewmodel.LoginViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding>() {
    override val layoutId = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()
    override val navArgs: LoginFragmentArgs by navArgs()
}