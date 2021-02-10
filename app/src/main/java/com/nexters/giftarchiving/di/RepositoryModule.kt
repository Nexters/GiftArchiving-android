package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.repository.LoginRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.repository.WriteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferenceRepository(get()) }
    single { LoginRepository(get()) }
    single { WriteRepository(get()) }
}