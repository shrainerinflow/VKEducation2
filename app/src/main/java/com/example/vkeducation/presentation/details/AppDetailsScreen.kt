package com.example.vkeducation.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkeducation.R
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.presentation.showcase.ShowcaseBlock
import kotlinx.coroutines.launch

@Composable
fun AppDetailsScreen(
    appId: Int,
    onBackClick: () -> Unit,
    onShowcaseAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppDetailsViewModel = viewModel(
        key = "details_$appId",
        factory = AppDetailsViewModel.factory(appId),
    ),
) {
    val state by viewModel.state.collectAsState()

    AppDetailsContent(
        state = state,
        onBackClick = onBackClick,
        onRetryClick = viewModel::load,
        onShowcaseAppClick = onShowcaseAppClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsContent(
    state: AppDetailsState,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    onShowcaseAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showUnderDevelopment: () -> Unit = {
        scope.launch { snackbarHostState.showSnackbar("Раздел в разработке") }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    IconButton(onClick = showUnderDevelopment) {
                        Icon(Icons.Default.Share, contentDescription = "Поделиться")
                    }
                },
            )
        },
    ) { innerPadding ->
        when (state) {
            AppDetailsState.Loading -> Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) { CircularProgressIndicator() }

            AppDetailsState.Error -> Column(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Не удалось загрузить приложение")
                Button(onClick = onRetryClick) { Text("Повторить") }
            }

            is AppDetailsState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                Header(state.app, Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = showUnderDevelopment,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                ) { Text("Установить") }
                Spacer(Modifier.height(16.dp))
                Screenshots()
                Spacer(Modifier.height(16.dp))
                Description(state.app.fullDescription, Modifier.padding(horizontal = 16.dp))
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
                TextButton(
                    onClick = showUnderDevelopment,
                    modifier = Modifier.padding(start = 8.dp),
                ) { Text("Разработчик: ${state.app.developer}") }
                Spacer(Modifier.height(16.dp))

                // Блок «Витрина» — в самом низу карточки
                ShowcaseBlock(apps = state.showcase, onAppClick = onShowcaseAppClick)
            }
        }
    }
}

@Composable
private fun Header(app: AppInfo, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = app.name,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
        )
        Column(Modifier.padding(start = 16.dp)) {
            Text(app.name, style = MaterialTheme.typography.headlineSmall)
            Text(app.developer, color = MaterialTheme.colorScheme.primary)
            Text(
                text = "${app.category} · ${app.ageRating}+ · ${app.sizeMb} МБ",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Composable
private fun Screenshots() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(listOf(1, 2, 3)) { index ->
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(240.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer),
                contentAlignment = Alignment.Center,
            ) { Text("Скриншот $index") }
        }
    }
}

@Composable
private fun Description(text: String, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier) {
        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
        )
        if (!expanded) {
            TextButton(onClick = { expanded = true }) { Text("Читать далее") }
        }
    }
}
