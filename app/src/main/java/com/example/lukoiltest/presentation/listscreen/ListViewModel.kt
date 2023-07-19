package com.example.lukoiltest.presentation.listscreen

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.usecases.list.ListUseCase
import com.example.domain.appobjects.AppResult
import com.example.lukoiltest.CharactersDataSource
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.base.BaseViewModel
import com.example.lukoiltest.presentation.detailscreen.DetailScreenArgs
import com.example.lukoiltest.presentation.navigation.Screens
import com.example.lukoiltest.presentation.utils.UiEvent
import com.example.lukoiltest.toArgs
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class ListViewModel : BaseViewModel<String, ListScreenEvent>() {

    private val useCase: ListUseCase by inject()

    init {
        fetch()
    }

    private fun fetch() {
        if(state.value is UiResult.Success) return
        updateState(UiResult.Pending())
        viewModelScope.launch {
            when (val api = useCase.execute()) {
                is AppResult.Error -> {
                    updateState(UiResult.Error(api.error))
                }

                is AppResult.Success -> {
                    updateState(UiResult.Success(api.data.characters))
                }
            }
        }
    }

    override fun onEvent(event: ListScreenEvent) {
        when (event) {
            is ListScreenEvent.OpenDetailScreen -> {
                val route = Screens.DetailScreen.withArgs(
                    toArgs(DetailScreenArgs(id = event.id))
                )
                sendUiEvent(UiEvent.Navigate(route = route))
            }

            ListScreenEvent.Retry -> {
                fetch()
            }
        }
    }

    fun getPager(url: String) = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = null,
        pagingSourceFactory = { CharactersDataSource(url) },
    ).flow.cachedIn(viewModelScope)
}