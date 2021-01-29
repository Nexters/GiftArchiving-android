package com.nexters.giftarchiving.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nexters.giftarchiving.R
import kotlinx.android.synthetic.main.fragment_taken.view.*
import java.lang.Math.abs
import java.time.temporal.ValueRange
import kotlin.math.ceil

class TakenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view: View = inflater.inflate(R.layout.fragment_taken, container, false)
        val viewPager = view.home_taken_viewpager
        val bgColors = arrayListOf<Int>(R.color.blue,R.color.orange,R.color.yellow)
        val people = arrayListOf<String>("test1","test2","test3")
        val dates = arrayListOf<String>("2021-01-29","2021-01-29","2021-01-29")

        val pageTransformer = PreviewSidePageTransformer()
        val itemDecoration = HorizontalMarginItemDecoration(requireContext(), R.dimen.viewpager_current_item_horizontal_margin)

        viewPager.apply {
            offscreenPageLimit = 1
            setPageTransformer(pageTransformer)
            addItemDecoration(itemDecoration)
            adapter = ItemViewPagerAdapter(bgColors, people, dates)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                var current = 0
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val valueAnimator = ValueAnimator.ofObject(ArgbEvaluator(),ContextCompat.getColor(context,bgColors[current]),ContextCompat.getColor(context,bgColors[position]))
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
        return view
    }

    inner class PreviewSidePageTransformer : ViewPager2.PageTransformer{

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

        override fun transformPage(page: View, position: Float) {
            page.apply {
                translationX = -pageTranslationX * position
                scaleY = 1 - (0.25f * Math.abs(position))
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