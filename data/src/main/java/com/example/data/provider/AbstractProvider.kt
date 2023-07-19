package com.example.data.provider

import com.example.domain.appobjects.AppResult

abstract class AbstractProvider {

    inline fun <T> execute(block: () -> T): AppResult<T> {
        return try {
            AppResult.Success(block())
        } catch (e: Throwable) {
            AppResult.Error(e)
        }
    }
}