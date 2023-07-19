package com.example.lukoiltest.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lukoiltest.UiResult
import com.example.lukoiltest.presentation.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

abstract class BaseViewModel<STATE_VALUE, UI_EVENT> : ViewModel(), KoinComponent {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow<UiResult<STATE_VALUE>>(UiResult.Pending())
    val state = _state.asStateFlow()

    protected fun updateState(value: UiResult<STATE_VALUE>) {
        _state.value = value
    }

    open fun onEvent(event: UI_EVENT) {}

    protected fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}