package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.HomeFragmentDirections
import kotlinx.coroutines.launch

internal class HomeViewModel : BaseViewModel() {

    val onClickGivenListButtonListener = View.OnClickListener(){
        onClickGivenListButton()
    }

    val onClickTakenListButtonListener = View.OnClickListener(){
        onClickTakenListButton()
    }

    fun onClickWrite(isReceiveGift: Boolean){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionHomeFragmentToWriteFragment(isReceiveGift = isReceiveGift)
        }
    }

    private fun onClickTakenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionTakenFragmentToListFragment("받은 선물")
        }
    }

    private fun onClickGivenListButton(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionGivenFragmentToListFragment("보낸 선물")
        }
    }

    fun onClickSetting(){
        viewModelScope.launch {
            navDirections.value=HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
        }
    }
}