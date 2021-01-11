package com.nexters.giftarchiving.base

import androidx.recyclerview.widget.DiffUtil

internal class BaseDiffItemCallback<T : BaseItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    override fun areContentsTheSame(oldItem: T, newItem: T) =
        oldItem.hashCode() == newItem.hashCode()
}