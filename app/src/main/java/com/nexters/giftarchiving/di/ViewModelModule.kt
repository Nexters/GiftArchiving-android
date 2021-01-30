package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.viewmodel.*
import com.nexters.giftarchiving.viewmodel.HomeViewModel
import com.nexters.giftarchiving.viewmodel.LoginViewModel
import com.nexters.giftarchiving.viewmodel.MainViewModel
import com.nexters.giftarchiving.viewmodel.SplashViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel() }
    factory { SplashViewModel() }
    factory { LoginViewModel() }
    factory { HomeViewModel() }
    factory { ListViewModel() }
}