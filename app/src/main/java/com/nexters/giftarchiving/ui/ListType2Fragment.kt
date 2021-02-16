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
import com.nexters.giftarchiving.viewmodel.ListViewModel

internal class ListType2Fragment(val giftList: List<GiftResponse>, val viewModel: ListViewModel) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_list_type2, container, false)

        val list_recyclerview = view.findViewById<RecyclerView>(R.id.list2_recyclerView)
        val recyclerViewAdapter = ListType2RecyclerviewAdapter(requireContext(),giftList, viewModel)
        list_recyclerview.adapter = recyclerViewAdapter
        val gridLayoutManager = GridLayoutManager(context,2)
        list_recyclerview.layoutManager = gridLayoutManager
        val dividerItemDecoration = DividerItemDecoration(context,LinearLayoutManager(requireContext()).orientation)
        list_recyclerview.addItemDecoration(dividerItemDecoration)
        list_recyclerview.addItemDecoration(RecyclerDecoration())
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