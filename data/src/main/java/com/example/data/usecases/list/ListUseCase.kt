package com.example.data.usecases.list

import com.example.data.provider.ApiProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListUseCase : KoinComponent {

    private val provider: ApiProvider by inject()

    suspend fun execute() = provider.getApi()
}