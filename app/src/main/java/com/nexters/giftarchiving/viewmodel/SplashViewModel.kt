package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.SplashFragmentDirections
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashViewModel : BaseViewModel() {
    init {
        viewModelScope.launch {
            delay(1000L)
            navDirections.value =
                SplashFragmentDirections.actionSplashFragmentToHomeFragment()
        }
    }
}