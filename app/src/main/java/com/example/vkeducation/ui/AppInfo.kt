package com.example.vkeducation.ui

import androidx.annotation.DrawableRes
import com.example.vkeducation.R

data class AppInfo(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    @DrawableRes val iconRes: Int,
)

val sampleApps: List<AppInfo> = listOf(
    AppInfo(1, "VK", "Социальная сеть: друзья, сообщества, музыка и видео", "Социальные сети", R.drawable.ic_launcher_foreground),
    AppInfo(2, "VK Музыка", "Слушайте любимые треки и подборки без границ", "Музыка", R.drawable.ic_launcher_foreground),
    AppInfo(3, "VK Мессенджер", "Быстрые сообщения, звонки и чаты", "Общение", R.drawable.ic_launcher_foreground),
    AppInfo(4, "VK Видео", "Фильмы, сериалы и ролики от авторов", "Видео", R.drawable.ic_launcher_foreground),
    AppInfo(5, "VK Pay", "Платежи и переводы в пару касаний", "Финансы", R.drawable.ic_launcher_foreground),
    AppInfo(6, "VK Клипы", "Короткие вертикальные видео на любой вкус", "Видео", R.drawable.ic_launcher_foreground),
    AppInfo(7, "VK Почта", "Удобный почтовый клиент для всех ящиков", "Продуктивность", R.drawable.ic_launcher_foreground),
    AppInfo(8, "VK Игры", "Мини-игры и большие хиты прямо в приложении", "Игры", R.drawable.ic_launcher_foreground),
)