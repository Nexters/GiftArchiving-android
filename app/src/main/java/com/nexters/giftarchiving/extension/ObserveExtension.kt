package com.nexters.giftarchiving.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal fun <T : Any, L : LiveData<T>> Fragment.observe(liveData: L, action: (T) -> Unit) =
    liveData.observe(
        viewLifecycleOwner,
        Observer { action(it) }
    )