package com.nexters.giftarchiving.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentGivenBinding
import com.nexters.giftarchiving.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class GivenFragment : BaseFragment<HomeViewModel, FragmentGivenBinding>() {
    override val layoutId = R.layout.fragment_given
    override val viewModel: HomeViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.homeGivenViewpager
        val bgColors = arrayListOf<Int>(R.color.orange,R.color.blue,R.color.yellow)
        val people = arrayListOf<String>("test1","test2","test3")
        val dates = arrayListOf<String>("2021-01-29","2021-01-29","2021-01-29")

        val pageTransformer = PreviewSidePageTransformer()
        val itemDecoration = HorizontalMarginItemDecoration(requireContext(), R.dimen.viewpager_current_item_horizontal_margin)

        viewPager.apply {
            offscreenPageLimit = 1
            clipToPadding = false
            setPageTransformer(pageTransformer)
            addItemDecoration(itemDecoration)
            adapter = ItemViewPagerAdapter(bgColors, people, dates)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                var current = 0
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val valueAnimator = ValueAnimator.ofObject(
                        ArgbEvaluator(),
                        ContextCompat.getColor(context,bgColors[current]),
                        ContextCompat.getColor(context,bgColors[position]))
                    Log.d("position",position.toString())
                    valueAnimator.apply {
                        duration = 500
                        addUpdateListener {
                            viewPager.setBackgroundColor(it.animatedValue as Int)
                        }
                        start()
                    }
                    current=position
                }
            })
        }
    }


    inner class PreviewSidePageTransformer : ViewPager2.PageTransformer{

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

        override fun transformPage(page: View, position: Float) {
            page.apply {
                translationX = -pageTranslationX * position
            }
        }
    }

    inner class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int) :
        RecyclerView.ItemDecoration() {

        private val horizontalMarginInPx: Int =
            context.resources.getDimension(horizontalMarginInDp).toInt()

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
        ) {
            outRect.apply {
                right = horizontalMarginInPx
                left = horizontalMarginInPx
            }
        }
    }
}