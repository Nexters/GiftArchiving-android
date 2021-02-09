package com.nexters.giftarchiving.di

import com.nexters.giftarchiving.BuildConfig
import com.nexters.giftarchiving.service.network.GiftZipService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netWorkModule = module {
    single {
        GsonConverterFactory.create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single {
        get<Retrofit>().create(GiftZipService::class.java)
    }
}