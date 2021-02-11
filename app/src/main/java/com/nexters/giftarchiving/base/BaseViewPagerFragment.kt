package com.nexters.giftarchiving.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.nexters.giftarchiving.BR
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast

internal abstract class BaseViewPagerFragment<VM : BaseViewModel, VB : ViewDataBinding> :
    Fragment() {
    abstract val layoutId: Int
    abstract val viewModel: VM

    lateinit var binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(BR.viewModel, viewModel)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.toast) { toast(it) }
    }
}