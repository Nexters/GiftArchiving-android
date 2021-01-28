package com.nexters.giftarchiving.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.nexters.giftarchiving.BR
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.extension.toast
import com.nexters.giftarchiving.util.BackDirections
import com.nexters.giftarchiving.util.EmptyNavArgs

internal abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding> : Fragment() {
    abstract val layoutId: Int
    abstract val viewModel: VM
    protected open val navArgs: NavArgs = EmptyNavArgs

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

        with(viewModel) {
            observe(toast) { toast(it) }
            observe(navDirections) { navigation(it) }
        }

        viewModel.navArgs(navArgs)
    }

    private fun navigation(navDirections: NavDirections) {
        if (navDirections is BackDirections) {
            if (navDirections.destinationId == -1) {
                findNavController().popBackStack()
            } else {
                with(navDirections) {
                    findNavController().popBackStack(destinationId, inclusive)
                }
            }
        } else {
            findNavController().navigate(navDirections)
        }
    }
}