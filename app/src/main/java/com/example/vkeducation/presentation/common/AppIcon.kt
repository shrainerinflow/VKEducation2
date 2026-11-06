package com.example.vkeducation.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vkeducation.R

/** Иконка приложения по ссылке (Coil). Пока грузится или при ошибке показывает заглушку. */
@Composable
fun AppIcon(
    url: String?,
    contentDescription: String?,
    size: Dp,
    cornerRadius: Dp,
    modifier: Modifier = Modifier,
) {
    val placeholder = painterResource(R.drawable.ic_launcher_foreground)
    AsyncImage(
        model = url,
        contentDescription = contentDescription,
        placeholder = placeholder,
        error = placeholder,
        fallback = placeholder,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(MaterialTheme.colorScheme.primaryContainer),
    )
}
