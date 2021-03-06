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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.base.BaseFragment
import com.nexters.giftarchiving.databinding.FragmentTakenBinding
import com.nexters.giftarchiving.extension.observe
import com.nexters.giftarchiving.model.GiftResponse
import com.nexters.giftarchiving.ui.viewpager.adapter.ItemViewPagerAdapter
import com.nexters.giftarchiving.viewmodel.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

internal class TakenFragment : BaseFragment<HomeViewModel, FragmentTakenBinding>() {
    override val layoutId = R.layout.fragment_taken
    override val viewModel: HomeViewModel by viewModels({requireParentFragment()})
    var current = 0
    val bgColors = arrayListOf<Int>(R.color.gray)
    val frames = arrayListOf<String>("SQUARE")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = binding.homeTakenViewpager
        observe(viewModel.getAllReceivedGiftListResponse){
            if(it.giftListGifts.isNotEmpty()){
                bgColors.clear()
                frames.clear()
                for(item in it.giftListGifts){
                    when (item.bgColor) {
                        "ORANGE", "pinkishOrange" -> bgColors.add(R.color.orange)
                        "YELLOW", "wheat" -> bgColors.add(R.color.yellow)
                        "BLUE", "ceruleanBlue" -> bgColors.add(R.color.blue)
                        else -> bgColors.add(R.color.gray)
                    }
                    frames.add(item.frameType)
                }
            } else{
                bgColors.clear()
                frames.clear()
                bgColors.add(R.color.gray)
                frames.add("SQUARE")
            }
        }
        val pageTransformer = PreviewSidePageTransformer()

        viewPager.apply {
            offscreenPageLimit = 1
            clipToPadding = false
            setPageTransformer(pageTransformer)
            observe(viewModel.getAllReceivedGiftListResponse) {
                adapter = if (it.giftListGifts.isEmpty()) {
                    val emptyGift = GiftResponse(
                        "empty",
                        "From. 보낸이",
                        "empty",
                        getString(R.string.home_default_taken),
                        "empty",
                        "empty",
                        "empty",
                        "",
                        "MONO",
                        true,
                        "SQUARE"
                    )
                    viewModel.setCurrentBgColorAndFrame(R.color.gray,"SQUARE")
                    ItemViewPagerAdapter(requireContext(), listOf(emptyGift), 0, null)
                } else {
                    ItemViewPagerAdapter(requireContext(), it.giftListGifts, 0) { id ->
                        viewModel.onClickDetail(id)
                    }
                }
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    val valueAnimator = ValueAnimator.ofObject(
                        ArgbEvaluator(),
                        ContextCompat.getColor(context, bgColors[current]),
                        ContextCompat.getColor(context, bgColors[position])
                    )
                    valueAnimator.apply {
                        duration = 500
                        addUpdateListener {
                            viewPager.setBackgroundColor(it.animatedValue as Int)
                        }
                        start()
                    }
                    viewModel.setCurrentBgColorAndFrame(bgColors[position], frames[position])
                    current = position
                }
            })
            val itemDecoration = HorizontalMarginItemDecoration(
                context,
                R.dimen.viewpager_current_item_margin
            )
            addItemDecoration(itemDecoration)
        }
        observe(viewModel.currentBgColor){
            if (it==R.color.yellow){
                binding.takenDetailButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
                binding.takenDetailButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_icon_arrow_bk,0)
            } else{
                binding.takenDetailButton.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                binding.takenDetailButton.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_icon_all,0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.needReload) current = 0
        viewModel.setCurrentBgColorAndFrame(bgColors[current],frames[current])
    }


    inner class PreviewSidePageTransformer : ViewPager2.PageTransformer{

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_margin)
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
            outRect.right = horizontalMarginInPx
            outRect.left = horizontalMarginInPx
        }
    }
}