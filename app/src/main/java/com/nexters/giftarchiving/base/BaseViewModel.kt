package com.nexters.giftarchiving.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import com.nexters.giftarchiving.util.EmptyNavArgs
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal open class BaseViewModel : ViewModel() {
    val navDirections = LiveEvent<NavDirections>()
    val toast = LiveEvent<String>()
    val navArgsChannel = MutableStateFlow<NavArgs>(EmptyNavArgs)

    fun navArgs(value: NavArgs) {
        viewModelScope.launch { navArgsChannel.emit(value) }
    }
}