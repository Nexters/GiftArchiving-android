package com.nexters.giftarchiving

import android.app.Application
import com.nexters.giftarchiving.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GiftArchivingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GiftArchivingApplication)
            modules(listOf(viewModelModule))
        }
    }
}