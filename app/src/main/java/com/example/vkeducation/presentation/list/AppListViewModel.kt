package com.example.vkeducation.presentation.list

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.usecase.GetAppsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface AppListState {
    data object Loading : AppListState
    data object Error : AppListState
    data class Content(val apps: List<AppInfo>) : AppListState
}

sealed interface AppListEvent {
    data object ShowLogoMessage : AppListEvent
}

@HiltViewModel
class AppListViewModel @Inject constructor(
    private val getAppsUseCase: GetAppsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AppListState>(AppListState.Loading)
    val state: StateFlow<AppListState> = _state.asStateFlow()

    private val _events = Channel<AppListEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            _state.value = AppListState.Loading
            _state.value = try {
                AppListState.Content(getAppsUseCase())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AppListState.Error
            }
        }
    }

    fun onLogoClick() {
        viewModelScope.launch { _events.send(AppListEvent.ShowLogoMessage) }
    }
}
