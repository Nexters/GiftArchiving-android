package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.viewmodel.*
import com.nexters.giftarchiving.viewmodel.CropViewModel
import com.nexters.giftarchiving.viewmodel.HomeViewModel
import com.nexters.giftarchiving.viewmodel.ListViewModel
import com.nexters.giftarchiving.viewmodel.LoginViewModel
import com.nexters.giftarchiving.viewmodel.MainViewModel
import com.nexters.giftarchiving.viewmodel.SettingsViewModel
import com.nexters.giftarchiving.viewmodel.ShareViewModel
import com.nexters.giftarchiving.viewmodel.SplashViewModel
import com.nexters.giftarchiving.viewmodel.WriteViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { MainViewModel() }
    factory { WriteViewModel(get(), get()) }
    factory { CropViewModel() }
    factory { ShareViewModel() }
    factory { SplashViewModel() }
    factory { LoginViewModel(get(), get()) }
    factory { HomeViewModel() }
    factory { ListViewModel() }
    factory { SettingsViewModel() }
}