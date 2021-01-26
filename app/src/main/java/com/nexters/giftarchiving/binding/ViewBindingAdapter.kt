package com.nexters.giftarchiving.binding

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.util.theme.BackgroundColorTheme

private fun getColorByResource(context: Context, @ColorRes colorId: Int) =
    ContextCompat.getColor(context, colorId)

@BindingAdapter("android:textColor")
fun setFontColor(tv: TextView, colorTheme: BackgroundColorTheme?) {
    val colorByResource =
        getColorByResource(tv.context, colorTheme?.fontColor ?: R.color.colorWhite)
    tv.setTextColor(colorByResource)
}

@BindingAdapter("android:background")
fun setBackgroundColor(v: View, colorTheme: BackgroundColorTheme?) {
    val colorByResource =
        getColorByResource(
            v.context,
            colorTheme?.backgroundColor ?: BackgroundColorTheme.MONO.backgroundColor
        )
    v.setBackgroundColor(colorByResource)
}

@BindingAdapter("android:background")
fun setBackgroundWithBitmap(iv: ImageView, bitmap: Bitmap?) {
    if (bitmap != null) {
        iv.setImageBitmap(bitmap)
    } else {
        iv.background = ContextCompat.getDrawable(iv.context, R.drawable.write_empty_image_background)
    }
}