package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.viewmodel.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel() }
}