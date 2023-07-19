package com.example.lukoiltest.presentation.detailscreen

import androidx.lifecycle.viewModelScope
import com.example.data.usecases.detail.DetailUseCase
import com.example.domain.apiobjects.CharacterDetailItem
import com.example.domain.appobjects.AppResult
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.base.BaseViewModel
import com.example.lukoiltest.presentation.episodesscreen.EpisodesScreenArgs
import com.example.lukoiltest.presentation.navigation.Screens
import com.example.lukoiltest.presentation.utils.UiEvent
import com.example.lukoiltest.toArgs
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class DetailViewModel : BaseViewModel<CharacterDetailItem, DetailScreenEvent>() {

    private val useCase: DetailUseCase by inject()

    fun getCharacter(id: Int) {
        if(state.value is UiResult.Success) return

        updateState(UiResult.Pending())
        viewModelScope.launch {
            when (val api = useCase.execute(id)) {
                is AppResult.Error -> {
                    updateState(UiResult.Error(api.error))
                }

                is AppResult.Success -> {
                    updateState(UiResult.Success(api.data))
                }
            }
        }
    }

    override fun onEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }

            is DetailScreenEvent.OpenEpisodes -> {
                val route = Screens.EpisodesScreen.withArgs(
                    toArgs(EpisodesScreenArgs(characterId = event.characterId))
                )
                sendUiEvent(UiEvent.Navigate(route = route))
            }

            is DetailScreenEvent.Retry -> {
                getCharacter(event.characterId)
            }
        }
    }

}