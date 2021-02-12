package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.binding.setVisibility
import com.nexters.giftarchiving.data.room.LatestSearch
import com.nexters.giftarchiving.data.room.LatestSearchDB
import com.nexters.giftarchiving.data.room.LatestSearchDao
import com.nexters.giftarchiving.databinding.FragmentSearchFilterBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchFilterFragment : BaseFragment<SearchViewModel, FragmentSearchFilterBinding>() {
    override val layoutId = R.layout.fragment_search_filter
    override val viewModel: SearchViewModel by viewModel()
    val latestSearchDao : LatestSearchDao by lazy { LatestSearchDB.getInstance(requireContext()).latestSearchDao() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val allLatestSearch = getAllLatestSearch()
        observe(allLatestSearch){
            if(it.isEmpty()){
                binding.searchLatestLayout.visibility = View.GONE
            } else{
                binding.searchLatestLayout.visibility = View.VISIBLE
            }
        }
    }

    fun getAllLatestSearch() : LiveData<List<LatestSearch>> {
        return latestSearchDao.getAll()
    }
}