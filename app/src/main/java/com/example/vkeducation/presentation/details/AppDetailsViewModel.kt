package com.example.vkeducation.presentation.details

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vkeducation.di.AppContainer
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.usecase.GetAppUseCase
import com.example.vkeducation.domain.usecase.GetShowcaseAppsUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Immutable
sealed interface AppDetailsState {
    data object Loading : AppDetailsState
    data object Error : AppDetailsState
    data class Content(val app: AppInfo, val showcase: List<AppInfo>) : AppDetailsState
}

class AppDetailsViewModel(
    private val appId: Int,
    private val getAppUseCase: GetAppUseCase,
    private val getShowcaseAppsUseCase: GetShowcaseAppsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val state: StateFlow<AppDetailsState> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _state.value = AppDetailsState.Loading
            _state.value = try {
                val app = getAppUseCase(appId)
                if (app == null) {
                    AppDetailsState.Error
                } else {
                    AppDetailsState.Content(app = app, showcase = getShowcaseAppsUseCase())
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AppDetailsState.Error
            }
        }
    }

    companion object {
        fun factory(appId: Int): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AppDetailsViewModel(
                    appId = appId,
                    getAppUseCase = AppContainer.getAppUseCase,
                    getShowcaseAppsUseCase = AppContainer.getShowcaseAppsUseCase,
                )
            }
        }
    }
}
