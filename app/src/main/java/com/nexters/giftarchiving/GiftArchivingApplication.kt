package com.nexters.giftarchiving

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
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
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}