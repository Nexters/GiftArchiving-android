package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.SettingsFragmentDirections
import com.nexters.giftarchiving.util.BackDirections
import kotlinx.coroutines.launch

internal class SettingsViewModel : BaseViewModel() {
    val opensourceNames = arrayListOf<String>("Kakao","Koin")
    val opensourceDetails = arrayListOf<String>("Kakao detail","Koin detail")
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

    fun onClickOpensource(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToOpensourceFragment()
        }
    }
    fun onClickPrivacy(){
        viewModelScope.launch {
            navDirections.value= SettingsFragmentDirections.actionSettingFragmentToPrivacyFragment()
        }
    }
}