package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.HomeFragmentDirections
import kotlinx.coroutines.launch

internal class HomeViewModel : BaseViewModel() {

    val onClickGivenListButtonListener = View.OnClickListener(){
        OnClickGivenListButton()
    }

    val onClickTakenListButtonListener = View.OnClickListener(){
        OnClickTakenListButton()
    }

    fun OnClickTakenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionTakenFragmentToListFragment("받은 선물")
        }
    }

    fun OnClickGivenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionGivenFragmentToListFragment("보낸 선물")
        }
    }
}