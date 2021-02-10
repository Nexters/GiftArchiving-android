package com.nexters.giftarchiving.di

import com.google.gson.GsonBuilder
import com.nexters.giftarchiving.BuildConfig
import com.nexters.giftarchiving.service.network.GiftZipService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val netWorkModule = module {
    single{
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        GsonConverterFactory.create(get())
    }

    single {
        ScalarsConverterFactory.create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<ScalarsConverterFactory>())
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

    single {
        get<Retrofit>().create(GiftZipService::class.java)
    }
}