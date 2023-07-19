package com.example.data.di

import com.example.data.api.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val apiModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }
}