package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentGivenBinding
import com.nexters.giftarchiving.databinding.FragmentSearchResultBinding
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchResultFragment : BaseFragment<SearchViewModel, FragmentSearchResultBinding>() {
    override val layoutId = R.layout.fragment_search_result
    override val viewModel: SearchViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_search_result, container, false)
        return view
    }
}