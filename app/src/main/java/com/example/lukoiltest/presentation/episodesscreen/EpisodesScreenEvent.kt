package com.example.lukoiltest.presentation.episodesscreen


sealed interface EpisodesScreenEvent {
    object OnBackClick : EpisodesScreenEvent
    data class Retry(val characterId: Int) : EpisodesScreenEvent
}