package com.nexters.giftarchiving.viewmodel

import android.view.View
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.SettingsFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.launch

internal class SettingsViewModel : BaseViewModel() {
    fun onClickBack() {
        navDirections.value = BackDirections()
    }
    fun onClickNotice(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToNoticeFragment()
        }
    }

    fun onClickTerms(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToTermsFragment()
        }
    }
}