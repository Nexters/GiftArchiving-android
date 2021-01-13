package com.nexters.giftarchiving.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import com.nexters.giftarchiving.util.EmptyNavArgs
import com.nexters.giftarchiving.util.LiveEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

internal open class BaseViewModel : ViewModel() {
    val navDirections = LiveEvent<NavDirections>()
    val toast = LiveEvent<String>()
    val navArgsFlow = MutableSharedFlow<NavArgs>()

    init {
        viewModelScope.launch { navArgsFlow.emit(EmptyNavArgs) }
    }

    fun navArgs(value: NavArgs) {
        viewModelScope.launch { navArgsFlow.emit(value) }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : NavArgs> navArgs(): Flow<T> = navArgsFlow
        .map { it as T }
        .filterNotNull()
}