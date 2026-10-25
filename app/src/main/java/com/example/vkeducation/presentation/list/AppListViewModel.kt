package com.example.vkeducation.presentation.list

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vkeducation.di.AppContainer
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.usecase.GetAppsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Immutable
sealed interface AppListState {
    data object Loading : AppListState
    data object Error : AppListState
    data class Content(val apps: List<AppInfo>) : AppListState
}

sealed interface AppListEvent {
    data object ShowLogoMessage : AppListEvent
}

class AppListViewModel(
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer { AppListViewModel(AppContainer.getAppsUseCase) }
        }
    }
}
