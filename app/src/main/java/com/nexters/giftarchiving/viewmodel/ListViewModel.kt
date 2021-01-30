package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.ListFragmentArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ListViewModel : BaseViewModel() {
    val listType = MutableLiveData<Boolean>()
    val title = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            navArgs<ListFragmentArgs>()
                .collect {
                    title.value = it.title
                }
            listType.value=false
        }
    }

    val onClickListSwitchListener = View.OnClickListener(){
        onClickListSwitchButton()
    }

    private fun onClickListSwitchButton(){

    }
}