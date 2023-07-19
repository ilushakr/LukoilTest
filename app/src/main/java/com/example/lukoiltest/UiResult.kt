package com.example.lukoiltest

sealed interface UiResult<T> {
    data class Success<T>(val data: T) : UiResult<T>
    data class Error<T>(val error: Throwable) : UiResult<T>
    data class Pending<T>(val data: T? = null) : UiResult<T>
}