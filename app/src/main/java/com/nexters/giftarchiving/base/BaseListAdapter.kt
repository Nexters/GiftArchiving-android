package com.nexters.giftarchiving.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nexters.giftarchiving.BR

internal abstract class BaseListAdapter<T : BaseItem>(
    diffItemCallback: DiffUtil.ItemCallback<T> = BaseDiffItemCallback()
) : ListAdapter<T, BaseViewHolder<T>>(diffItemCallback) {
    private var viewModel: BaseViewModel? = null

    abstract fun getItemViewTypeByItemType(item: T): Int

    override fun onViewAttachedToWindow(holder: BaseViewHolder<T>) {
        super.onViewAttachedToWindow(holder)
        holder.attach()
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        holder.detach()
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypeByItemType(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val inflate = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflate, viewType, parent, false)
        val viewHolder = BaseViewHolder<T>(binding)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = viewHolder
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(getItem(position))
    }

    fun setViewModel(vm: BaseViewModel) {
        viewModel = vm
    }
}