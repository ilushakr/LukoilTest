package com.example.data.di

import com.example.data.usecases.detail.DetailUseCase
import com.example.data.usecases.episodes.EpisodesUseCase
import com.example.data.usecases.list.DataSourceUseCase
import com.example.data.usecases.list.ListUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory {
        DetailUseCase()
    }

    factory {
        EpisodesUseCase()
    }

    factory {
        ListUseCase()
    }

    factory {
        DataSourceUseCase()
    }
}