package com.example.data.di

import com.example.data.provider.ApiProvider
import com.example.data.provider.ApiProviderImpl
import org.koin.dsl.module

val providerModule = module {
    single<ApiProvider> {
        ApiProviderImpl(get())
    }
}