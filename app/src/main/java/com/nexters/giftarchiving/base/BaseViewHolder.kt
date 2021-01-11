package com.nexters.giftarchiving.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import com.nexters.giftarchiving.BR

internal open class BaseViewHolder<T : BaseItem>(
    private val viewDataBinding: ViewDataBinding
) : RecyclerView.ViewHolder(viewDataBinding.root), LifecycleOwner {
    private val lifecycleRegister by lazy(LazyThreadSafetyMode.NONE) { LifecycleRegistry(this) }

    init {
        lifecycleRegister.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }


    override fun getLifecycle() = lifecycleRegister

    open fun bind(item: T) {
        bind(-1, item)
    }

    open fun bind(pos: Int, item: T) {
        viewDataBinding.setVariable(BR.item, item)
        viewDataBinding.executePendingBindings()
    }

    fun attach() {
        lifecycleRegister.handleLifecycleEvent(Lifecycle.Event.ON_START)
    }

    fun detach() {
        lifecycleRegister.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }
}