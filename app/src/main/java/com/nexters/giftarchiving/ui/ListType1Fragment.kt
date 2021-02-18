package com.nexters.giftarchiving.ui

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.ui.viewpager.adapter.ItemViewPagerAdapter
import com.nexters.giftarchiving.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list_type1.view.*

internal class ListType1Fragment(val giftList: List<GiftResponse>, val viewModel: ListViewModel, val isReceived : Boolean) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_list_type1, container, false)
        val viewPager = view.list1_viewPager


        val pageTransformer = CardSwipePageTransformer()

        viewPager.apply {
            val callback: (String) -> Unit = { viewModel.onClickDetail(it) }
            offscreenPageLimit = 1
            clipToPadding = false
            if(isReceived){
                adapter = ItemViewPagerAdapter(requireContext(),giftList,2, callback)
            } else{
                adapter = ItemViewPagerAdapter(requireContext(),giftList,3, callback)
            }
            setPadding( resources.getDimension(R.dimen.list1_viewpager_padding).toInt(),0,resources.getDimension(R.dimen.list1_viewpager_padding).toInt(),0)
            setPageTransformer(pageTransformer)
            val itemDecoration = VerticalMarginItemDecoration(
                context,
                R.dimen.viewpager_current_item_margin
            )
            addItemDecoration(itemDecoration)
        }
        return view
    }

    inner class CardSwipePageTransformer : ViewPager2.PageTransformer{

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)/2
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_margin)
        val pageTranslationY = nextItemVisiblePx + currentItemHorizontalMarginPx

        override fun transformPage(view: View, position: Float) {
            view.apply {
                val pageHeight = height+2*currentItemHorizontalMarginPx
                when {
                    position < -1 -> {
                        alpha = 0f
                    }
                    position <= 0 -> {
                        alpha = 1f
                        translationY = pageHeight * -position
                        translationZ = -1f
                    }
                    position <= 1 -> {
                        translationY = -pageTranslationY*position
                        alpha = 1f
                        translationZ = 0f
                    }
                    else -> {
                        alpha = 1f
                    }
                }
            }
        }
    }

    inner class VerticalMarginItemDecoration(context: Context, @DimenRes verticalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val verticalMarginInPx: Int =
            context.resources.getDimension(verticalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.top = verticalMarginInPx
            outRect.bottom = verticalMarginInPx
        }
    }
}