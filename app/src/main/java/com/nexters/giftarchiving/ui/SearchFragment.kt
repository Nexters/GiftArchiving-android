package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.data.room.LatestSearch
import com.nexters.giftarchiving.data.room.LatestSearchDB
import com.nexters.giftarchiving.data.room.LatestSearchDao
import com.nexters.giftarchiving.databinding.FragmentSearchBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchFragment : BaseFragment<SearchViewModel, FragmentSearchBinding>() {
    override val layoutId = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cFM = childFragmentManager.beginTransaction()
        observe(viewModel.fragmentType){
            when(it){
                0->cFM.replace(R.id.search_fragment, SearchFilterFragment()).commit()
                1->cFM.replace(R.id.search_fragment, SearchResultFragment()).commit()
                2->cFM.replace(R.id.search_fragment, SearchNoneFragment()).commit()
                3->cFM.replace(R.id.search_fragment, SearchErrorFragment()).commit()
            }
        }
    }


}