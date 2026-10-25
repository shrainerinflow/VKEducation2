package com.example.vkeducation.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkeducation.R
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.presentation.common.AppListItem
import com.example.vkeducation.ui.theme.VKEducationTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun AppListScreen(
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppListViewModel = viewModel(factory = AppListViewModel.Factory),
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveEvents(events = viewModel.events, snackbarHostState = snackbarHostState)

    AppListContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onLogoClick = viewModel::onLogoClick,
        onRetryClick = viewModel::loadApps,
        onAppClick = onAppClick,
        modifier = modifier,
    )
}

@Composable
private fun ObserveEvents(
    events: Flow<AppListEvent>,
    snackbarHostState: SnackbarHostState,
) {
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                AppListEvent.ShowLogoMessage ->
                    snackbarHostState.showSnackbar("Вы нажали на логотип")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListContent(
    state: AppListState,
    snackbarHostState: SnackbarHostState,
    onLogoClick: () -> Unit,
    onRetryClick: () -> Unit,
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onLogoClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_launcher_foreground),
                            contentDescription = "Логотип приложения",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(40.dp),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            )
        },
    ) { innerPadding ->
        when (state) {
            AppListState.Loading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            AppListState.Error -> Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Не удалось загрузить список")
                Button(onClick = onRetryClick) { Text("Повторить") }
            }

            is AppListState.Content -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(items = state.apps, key = { it.id }) { app ->
                    AppListItem(app = app, onClick = { onAppClick(app) })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppListContentPreview() {
    VKEducationTheme {
        AppListContent(
            state = AppListState.Content(
                listOf(
                    AppInfo(1, "VK", "Социальная сеть", "Социальная сеть", "Социальные сети", "VK Play", 12, 100f),
                    AppInfo(2, "VK Музыка", "Любимые треки", "Любимые треки", "Музыка", "VK Play", 12, 80f),
                )
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onLogoClick = {},
            onRetryClick = {},
            onAppClick = {},
        )
    }
}
