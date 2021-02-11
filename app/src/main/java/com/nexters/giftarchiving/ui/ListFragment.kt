package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentListBinding
import com.nexters.giftarchiving.ui.viewpager.adapter.ListViewPagerAdapter
import com.nexters.giftarchiving.viewmodel.ListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class ListFragment : BaseFragment<ListViewModel, FragmentListBinding>() {
    override val layoutId = R.layout.fragment_list
    override val viewModel: ListViewModel by viewModel()
    override val navArgs: ListFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listTypeViewPager = binding.listViewPager
        val switchButton = binding.listStyleButton
        listTypeViewPager.isUserInputEnabled = false
        listTypeViewPager.adapter = ListViewPagerAdapter(this)
        listTypeViewPager.setPageTransformer(ViewPager2.PageTransformer { page, position ->
            page.apply {
                val pageWidth = width
                when {
                    position < -0.5f -> {
                        alpha = 0f
                        translationX = pageWidth * -position
                        translationZ = -1f
                    }
                    position <= 0.5f -> {
                        translationX = pageWidth*-position
                        alpha = 1f
                        translationZ = 0f
                    }
                    else -> {
                        translationX = pageWidth*position
                        alpha = 0f
                    }
                }
            }
        })
        viewModel.listType.observe(this.viewLifecycleOwner, Observer {
            if (it){
                listTypeViewPager.currentItem = 0
                switchButton.setImageResource(R.drawable.ic_icon_2_grid)
            } else{
                listTypeViewPager.currentItem = 1
                switchButton.setImageResource(R.drawable.ic_icon_1_grid)
            }
        })
    }
}