package com.example.vkeducation.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vkeducation.ui.theme.VKEducationTheme
import kotlinx.coroutines.launch

/** Данные карточки (захардкожены, строятся из AppInfo). */
data class AppDetails(
    val app: AppInfo,
    val developer: String,
    val ageRating: Int,
    val sizeMb: Float,
    val description: String,
)

fun AppInfo.toDetails() = AppDetails(
    app = this,
    developer = "VK Play",
    ageRating = 12,
    sizeMb = 223.7f,
    description = "$description. Лёгкий и быстрый интерфейс, регулярные обновления и " +
            "поддержка на всех устройствах. Установите приложение и оцените все возможности " +
            "самостоятельно: мы постоянно добавляем новые функции и улучшаем стабильность работы.",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    details: AppDetails,
    showcaseApps: List<AppInfo>,
    onBackClick: () -> Unit,
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
        ) {
            Header(details, Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = showUnderDevelopment,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) { Text("Установить") }
            Spacer(Modifier.height(16.dp))
            Screenshots()
            Spacer(Modifier.height(16.dp))
            Description(details.description, Modifier.padding(horizontal = 16.dp))
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
            DeveloperRow(details.developer, onClick = showUnderDevelopment)
            Spacer(Modifier.height(16.dp))

            // Блок «Витрина» — в самом низу карточки
            ShowcaseBlock(apps = showcaseApps, onAppClick = onShowcaseAppClick)
        }
    }
}

@Composable
private fun Header(details: AppDetails, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(details.app.iconRes),
            contentDescription = details.app.name,
            modifier = Modifier
                .size(96.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
        )
        Column(Modifier.padding(start = 16.dp)) {
            Text(details.app.name, style = MaterialTheme.typography.headlineSmall)
            Text(details.developer, color = MaterialTheme.colorScheme.primary)
            Text(
                text = "${details.app.category} · ${details.ageRating}+ · ${details.sizeMb} МБ",
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

@Composable
private fun DeveloperRow(name: String, onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = Modifier.padding(start = 8.dp)) {
        Text("Разработчик: $name")
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDetailsScreenPreview() {
    VKEducationTheme {
        AppDetailsScreen(
            details = sampleApps.first().toDetails(),
            showcaseApps = sampleShowcase,
            onBackClick = {},
            onShowcaseAppClick = {},
        )
    }
}