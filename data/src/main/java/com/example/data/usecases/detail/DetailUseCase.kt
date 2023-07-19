package com.example.data.usecases.detail

import com.example.data.provider.ApiProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DetailUseCase : KoinComponent {

    private val provider: ApiProvider by inject()

    suspend fun execute(id: Int) = provider.getCharacter(id)
}