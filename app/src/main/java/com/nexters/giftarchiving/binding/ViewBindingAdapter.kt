package com.nexters.giftarchiving.binding

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.nexters.giftarchiving.R
import com.nexters.giftarchiving.data.write.WriteInformationMenu
import com.nexters.giftarchiving.util.ThemeBackgroundColorChangeAnimator
import com.nexters.giftarchiving.ui.data.BackgroundColorTheme
import com.nexters.giftarchiving.ui.data.write.WriteFrameShape
import com.nexters.giftarchiving.ui.data.write.WriteMenu
import com.nexters.giftarchiving.ui.data.write.WriteStickerTabLayoutTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

private fun getColorByResource(context: Context, @ColorRes colorId: Int) =
    ContextCompat.getColor(context, colorId)

@BindingAdapter("android:textColor")
internal fun setFontColor(tv: TextView, colorTheme: BackgroundColorTheme?) {
    val colorRes =
        if (colorTheme?.isDarkMode == false) {
            R.color.colorDarkGray
        } else {
            R.color.colorWhite
        }
    val colorByResource =
        getColorByResource(tv.context, colorRes)
    tv.setTextColor(colorByResource)
}

@BindingAdapter("android:textColorHint")
internal fun setFontHintColor(et: EditText, colorTheme: BackgroundColorTheme?) {
    val colorRes =
        if (colorTheme?.isDarkMode == false) {
            R.color.colorDarkGray
        } else {
            R.color.colorWhite
        }
    val colorByResource =
        getColorByResource(et.context, colorRes)
    et.setHintTextColor(colorByResource)
}

@BindingAdapter("android:background")
internal fun setBackgroundColor(v: View, colorTheme: BackgroundColorTheme?) {
    val colorByResource =
        getColorByResource(
            v.context,
            colorTheme?.backgroundColor ?: BackgroundColorTheme.MONO.backgroundColor
        )
    v.setBackgroundColor(colorByResource)
}

@BindingAdapter("android:background")
internal fun setBackgroundDrawable(v: View, @DrawableRes drawableRes: Int) {
    v.setBackgroundResource(drawableRes)
}

@BindingAdapter("android:src")
internal fun setSrcWithUrl(iv: ImageView, url: String?) {
    url?.let {
        Glide.with(iv.context)
            .load(it)
            .into(iv)
    }
}

@BindingAdapter("android:backgroundTint")
internal fun setBackgroundTintColor(v: View, colorTheme: BackgroundColorTheme) {
    v.backgroundTintList = ContextCompat.getColorStateList(v.context, colorTheme.backgroundColor)
}

@BindingAdapter("popUpBackground")
internal fun setPopUpBackgroundColor(v: View, colorTheme: BackgroundColorTheme?) {
    val colorByResource =
        getColorByResource(
            v.context,
            colorTheme?.popupBackgroundColor ?: BackgroundColorTheme.MONO.popupBackgroundColor
        )
    v.setBackgroundColor(colorByResource)
}

@BindingAdapter("theme", "informationMenu")
internal fun setInformationIconBackgroundColor(
    iv: View,
    theme: BackgroundColorTheme,
    menu: WriteInformationMenu
) {
    menu.let {
        val background = when (theme.isDarkMode) {
            true -> it.lightIconRes
            false -> it.darkIconRes
        }
        iv.setBackgroundResource(background)
    }

}

@BindingAdapter("backgroundWithAnimation")
internal fun setBackgroundColorWithAnimation(v: View, colorTheme: BackgroundColorTheme?) {
    val themeBackgroundColorRes =
        colorTheme?.backgroundColor ?: BackgroundColorTheme.MONO.backgroundColor
    val themeBackgroundColor = getColorByResource(v.context, themeBackgroundColorRes)

    if (v.background is ColorDrawable) {
        val from = (v.background as ColorDrawable).color
        ThemeBackgroundColorChangeAnimator(v, from, themeBackgroundColor).start()
    } else {
        v.setBackgroundColor(themeBackgroundColor)
    }
}

@BindingAdapter("android:background")
internal fun setBackgroundWithBitmap(iv: ImageView, bitmap: Bitmap?) {
    bitmap?.let {
        iv.setImageBitmap(it)
    }
}

@BindingAdapter("android:src")
internal fun setSrcWithBitmap(iv: ImageView, bitmap: Bitmap?) {
    bitmap?.let {
        iv.setImageBitmap(it)
    }
}

@BindingAdapter("android:src")
internal fun setSrcWithDrawableResId(iv: ImageView, @DrawableRes resId: Int?) {
    resId?.let {
        iv.setImageResource(resId)
    }
}

@BindingAdapter("android:visibility")
internal fun setVisibility(v: View, isVisible: Boolean) {
    v.visibility = when (isVisible) {
        true -> View.VISIBLE
        false -> View.GONE
    }
}

@BindingAdapter("localDateWithFormat")
internal fun setLocalDate(tv: TextView, localDate: LocalDate) {
    tv.text = localDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd. EEE", Locale.ENGLISH))
}

@BindingAdapter("tabTextColorsByTheme")
internal fun setTabLayoutTextColors(tl: TabLayout, theme: WriteStickerTabLayoutTheme) {
    val defaultColor = getColorByResource(tl.context, theme.tabTextColor)
    val selectColor = getColorByResource(tl.context, theme.tabSelectedTextColor)
    tl.setTabTextColors(defaultColor, selectColor)
}

@BindingAdapter("tabIndicatorBackgroundByTheme")
internal fun setWriteStickerTabLayoutTheme(tl: TabLayout, colorTheme: BackgroundColorTheme) {
    val selectColorRes = when (colorTheme.isDarkMode) {
        true -> R.color.white
        false -> R.color.colorDarkGray
    }
    val selectColor = getColorByResource(tl.context, selectColorRes)
    tl.setSelectedTabIndicatorColor(selectColor)
}

@BindingAdapter("theme", "emptyFrameShape")
internal fun setEmptyFrameShape(v: View, theme: BackgroundColorTheme, frameShape: WriteFrameShape) {
    when (frameShape) {
        WriteFrameShape.SQUARE -> {
            if (theme.isDarkMode) R.drawable.write_empty_image_background_rectangle_white
            else R.drawable.write_empty_image_background_rectangle_black
        }
        WriteFrameShape.CIRCLE -> {
            if (theme.isDarkMode) R.drawable.write_empty_image_background_oval_white
            else R.drawable.write_empty_image_background_oval_black
        }
        WriteFrameShape.ARCH -> {
            if (theme.isDarkMode) R.drawable.write_empty_image_background_window_white
            else R.drawable.write_empty_image_background_window_black
        }
    }.let { v.setBackgroundResource(it) }
}

@BindingAdapter("iconFrameShape")
internal fun setIconFrameShape(v: View, frameShape: WriteFrameShape) {
    when (frameShape) {
        WriteFrameShape.SQUARE -> R.drawable.frame_background_rectangle
        WriteFrameShape.CIRCLE -> R.drawable.frame_background_oval
        WriteFrameShape.ARCH -> R.drawable.frame_background_window
    }.let { v.setBackgroundResource(it) }
}

@BindingAdapter("theme", "backgroundColorByMenu")
internal fun setBackgroundColorByMenu(v: View, colorTheme: BackgroundColorTheme, menu: WriteMenu?) {
    when (menu?.isShowBottomBar) {
        true -> colorTheme.popupBackgroundColor
        else -> colorTheme.backgroundColor
    }.let { v.setBackgroundColor(getColorByResource(v.context, it)) }
}