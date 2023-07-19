package com.example.lukoiltest

import android.app.Application
import com.example.data.di.apiModule
import com.example.data.di.providerModule
import com.example.data.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(apiModule, providerModule, useCaseModule)
        }
    }
}