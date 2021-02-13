package com.nexters.giftarchiving.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.ui.recyclerview.adapter.ListType2RecyclerviewAdapter

class SearchResultRecyclerFragment(val giftList: ArrayList<GiftResponse>) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_search_result_recycler, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_result_recyclerView)
        val recyclerViewAdapter = ListType2RecyclerviewAdapter(requireContext(),giftList)
        recyclerView.adapter = recyclerViewAdapter
        val gridLayoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = gridLayoutManager
        val dividerItemDecoration = DividerItemDecoration(context,
            LinearLayoutManager(requireContext()).orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.addItemDecoration(RecyclerDecoration())
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