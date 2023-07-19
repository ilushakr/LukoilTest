package com.example.lukoiltest.presentation.detailscreen

sealed interface DetailScreenEvent {
    object OnBackClick : DetailScreenEvent
    data class OpenEpisodes(val characterId: Int) : DetailScreenEvent
    data class Retry(val characterId: Int) : DetailScreenEvent
}