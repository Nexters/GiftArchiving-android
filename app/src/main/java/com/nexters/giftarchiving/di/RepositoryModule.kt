package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.repository.LoginRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferenceRepository(get()) }
    single { LoginRepository(get()) }
}