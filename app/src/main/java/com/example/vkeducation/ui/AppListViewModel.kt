package com.example.vkeducation.ui

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Immutable
data class AppListState(
    val apps: List<AppInfo> = emptyList(),
)

sealed interface AppListEvent {
    data object ShowLogoMessage : AppListEvent
}

class AppListViewModel : ViewModel() {

    private val _state = MutableStateFlow(AppListState(apps = sampleApps))
    val state: StateFlow<AppListState> = _state.asStateFlow()

    private val _events = Channel<AppListEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onLogoClick() {
        viewModelScope.launch {
            _events.send(AppListEvent.ShowLogoMessage)
        }
    }
}