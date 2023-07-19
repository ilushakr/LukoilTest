package com.example.lukoiltest.presentation.listscreen

sealed interface ListScreenEvent{
    data class OpenDetailScreen(val id: Int): ListScreenEvent
    object Retry: ListScreenEvent
}

