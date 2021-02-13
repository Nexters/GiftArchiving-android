package com.nexters.giftarchiving.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentGivenBinding
import com.nexters.giftarchiving.databinding.FragmentSearchResultBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.ui.viewpager.adapter.HomeViewPagerAdapter
import com.nexters.giftarchiving.ui.viewpager.adapter.SearchResultViewPagerAdapter
import com.nexters.giftarchiving.viewmodel.SearchViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class SearchResultFragment : BaseFragment<SearchViewModel, FragmentSearchResultBinding>() {
    override val layoutId = R.layout.fragment_search_result
    override val viewModel: SearchViewModel by viewModels({requireParentFragment()})


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.searchResultTabLayout
        val viewPager = binding.searchResultViewPager
        val givenList = arrayListOf<GiftResponse>()
        val takenList = arrayListOf<GiftResponse>()
        var tabTextList : ArrayList<String>
        observe(viewModel.searchResultTaken){
            takenList.addAll(it)
            tabTextList = arrayListOf(String.format("%s %d",getString(R.string.search_result_taken), takenList.size), String.format("%s %d",getString(R.string.search_result_given), givenList.size))
            viewPager.isUserInputEnabled = false
            viewPager.adapter = SearchResultViewPagerAdapter(this, takenList, givenList)
            viewPager.setPageTransformer(ViewPager2.PageTransformer { page, position ->
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
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = String.format(tabTextList[position])
            }.attach()
        }
        observe(viewModel.searchResultGiven){
            givenList.addAll(it)
            tabTextList = arrayListOf(String.format("%s %d",getString(R.string.search_result_taken), takenList.size), String.format("%s %d",getString(R.string.search_result_given), givenList.size))
            viewPager.isUserInputEnabled = false
            viewPager.adapter = SearchResultViewPagerAdapter(this, takenList, givenList)
            viewPager.setPageTransformer(ViewPager2.PageTransformer { page, position ->
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
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = String.format(tabTextList[position])
            }.attach()
        }

    }

}