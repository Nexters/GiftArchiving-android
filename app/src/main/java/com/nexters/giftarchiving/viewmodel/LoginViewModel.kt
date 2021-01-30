package com.nexters.giftarchiving.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nexters.giftarchiving.base.BaseViewModel
import com.nexters.giftarchiving.ui.LoginFragmentArgs
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class LoginViewModel : BaseViewModel() {
    val testArg = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            navArgs<LoginFragmentArgs>()
                .collect {
                    testArg.value = it.testArgument
                }
        }
    }
}