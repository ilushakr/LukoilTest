package com.example.lukoiltest.presentation.episodesscreen

import androidx.lifecycle.viewModelScope
import com.example.data.usecases.episodes.EpisodesUseCase
import com.example.domain.apiobjects.Episode
import com.example.domain.appobjects.AppResult
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.base.BaseViewModel
import com.example.lukoiltest.presentation.utils.UiEvent
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class EpisodesViewModel : BaseViewModel<List<Episode>, EpisodesScreenEvent>() {

    private val useCase: EpisodesUseCase by inject()

    fun getEpisodes(id: Int) {
        if(state.value is UiResult.Success) return
        updateState(UiResult.Pending())
        viewModelScope.launch {
            when (val episodes = useCase.execute(id)) {
                is AppResult.Error -> {
                    updateState(UiResult.Error(episodes.error))
                }

                is AppResult.Success -> {
                    updateState(UiResult.Success(episodes.data))
                }
            }
        }
    }

    override fun onEvent(event: EpisodesScreenEvent) {
        when (event) {
            is EpisodesScreenEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is EpisodesScreenEvent.Retry -> {
                getEpisodes(event.characterId)
            }
        }
    }
}