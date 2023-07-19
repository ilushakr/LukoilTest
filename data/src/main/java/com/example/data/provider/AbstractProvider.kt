package com.example.data.provider

import com.example.domain.appobjects.AppResult

abstract class AbstractProvider {

    suspend fun <T> execute(block: suspend () -> T): AppResult<T> {
        return try {
            AppResult.Success(block())
        } catch (e: Throwable) {
            AppResult.Error(e)
        }
    }
}