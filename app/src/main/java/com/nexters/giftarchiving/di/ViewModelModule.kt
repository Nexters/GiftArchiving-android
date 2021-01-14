package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.viewmodel.MainViewModel
import com.nexters.giftarchiving.viewmodel.WriteViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel() }
    factory { WriteViewModel() }
}