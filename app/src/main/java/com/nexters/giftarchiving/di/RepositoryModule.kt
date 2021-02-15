package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.repository.*
import com.nexters.giftarchiving.repository.GiftRepository
import com.nexters.giftarchiving.repository.LoginRepository
import com.nexters.giftarchiving.repository.NoticeRepository
import com.nexters.giftarchiving.repository.PreferenceRepository
import com.nexters.giftarchiving.repository.WriteRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { PreferenceRepository(get()) }
    single { LoginRepository(get()) }
    single { WriteRepository(get()) }
    single { GiftRepository(get()) }
    single { NoticeRepository(get()) }
}