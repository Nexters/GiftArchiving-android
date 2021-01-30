package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nexters.giftarchiving.R

class ListType2Fragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_list_type2, container, false)

        val bgColors = arrayListOf<Int>(R.color.orange, R.color.blue, R.color.yellow)
        val people = arrayListOf<String>("test1", "test2", "test3")
        val dates = arrayListOf<String>("2021-01-29", "2021-01-29", "2021-01-29")

        return view
    }
}