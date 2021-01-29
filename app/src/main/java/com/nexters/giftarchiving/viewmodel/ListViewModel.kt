package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.ListFragmentArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ListViewModel : BaseViewModel() {
    val listType = MutableLiveData<Int>()
    val title = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            navArgs<ListFragmentArgs>()
                .collect {
                    title.value = it.title
                }
        }
    }
}