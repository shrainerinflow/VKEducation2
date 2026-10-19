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

// Витрина: ровно 9 приложений = 3 страницы по 3
val sampleShowcase: List<AppInfo> = listOf(
    AppInfo(11, "VK Облако", "Храните фото и файлы в безопасности", "Утилиты", R.drawable.ic_launcher_foreground),
    AppInfo(12, "VK Карты", "Навигация и маршруты по городу", "Навигация", R.drawable.ic_launcher_foreground),
    AppInfo(13, "VK Работа", "Поиск вакансий и резюме", "Работа", R.drawable.ic_launcher_foreground),
    AppInfo(14, "VK Знания", "Курсы и обучающие материалы", "Образование", R.drawable.ic_launcher_foreground),
    AppInfo(15, "VK Такси", "Быстрый заказ поездки", "Транспорт", R.drawable.ic_launcher_foreground),
    AppInfo(16, "VK Фото", "Редактор и альбомы для снимков", "Фото", R.drawable.ic_launcher_foreground),
    AppInfo(17, "VK Здоровье", "Трекер активности и сна", "Здоровье", R.drawable.ic_launcher_foreground),
    AppInfo(18, "VK Книги", "Электронные и аудиокниги", "Книги", R.drawable.ic_launcher_foreground),
    AppInfo(19, "VK Маркет", "Покупки у продавцов рядом", "Покупки", R.drawable.ic_launcher_foreground),
)

val allApps: List<AppInfo> = sampleApps + sampleShowcase
