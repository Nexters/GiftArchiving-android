package com.nexters.giftarchiving.binding

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.nexters.giftarchiving.util.color.BackgroundColor
import com.nexters.giftarchiving.util.color.FontColor

private fun getColorByResource(context: Context, @ColorRes colorId: Int) =
    ContextCompat.getColor(context, colorId)

@BindingAdapter("android:background")
fun setBackgroundColor(v: View, color: BackgroundColor?) {
    val colorByResource =
        getColorByResource(v.context, color?.resId ?: BackgroundColor.WHITE.resId)
    v.setBackgroundColor(colorByResource)
}

@BindingAdapter("android:textColor")
fun setFontColor(tv: TextView, color: FontColor?) {
    val colorByResource =
        getColorByResource(tv.context, color?.resId ?: FontColor.BLACK.resId)
    tv.setTextColor(colorByResource)
}
