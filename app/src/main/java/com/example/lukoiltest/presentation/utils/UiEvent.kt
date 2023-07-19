package com.example.lukoiltest.presentation.utils

sealed interface UiEvent {
    object PopBackStack : UiEvent
    data class Navigate(val route: String) : UiEvent
}