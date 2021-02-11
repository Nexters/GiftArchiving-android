package com.nexters.giftarchiving.util

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.IdRes

internal class ThemeBackgroundColorChangeAnimator(
    val view: View,
    @IdRes val from: Int,
    @IdRes val to: Int,
    duration: Long? = DURATION
) : ValueAnimator() {
    init {
        setIntValues(from, to)
        setEvaluator(ArgbEvaluator())
        addUpdateListener { view.setBackgroundColor(it.animatedValue.toString().toInt()) }
        setDuration(duration ?: DURATION)
    }

    companion object {
        const val DURATION = 100L
    }
}