package com.nexters.giftarchiving.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentSearchResultRecyclerBinding
import com.nexters.giftarchiving.ui.recyclerview.adapter.ListType2RecyclerviewAdapter
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchResultRecyclerFragment : BaseFragment<SearchViewModel, FragmentSearchResultRecyclerBinding>() {
    override val layoutId = R.layout.fragment_search_result_recycler
    override val viewModel: SearchViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.searchResultRecyclerView
        val recyclerViewAdapter = ListType2RecyclerviewAdapter(requireContext(),viewModel.getAllNotReceivedGiftListResponse.value!!)
        recyclerView.adapter = recyclerViewAdapter
        val gridLayoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = gridLayoutManager
        val dividerItemDecoration = DividerItemDecoration(context,
            LinearLayoutManager(requireContext()).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.addItemDecoration(RecyclerDecoration())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_search_result_recycler, container, false)

        return view
    }

    inner class RecyclerDecoration : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left=12
            outRect.right=12
            outRect.bottom=12
        }
    }
}