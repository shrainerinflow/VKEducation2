package com.example.vkeducation.domain.model

/** Доменная модель приложения. Ничего не знает ни про Android, ни про сеть. */
data class AppInfo(
    val id: Int,
    val name: String,
    val description: String,
    val fullDescription: String,
    val category: String,
    val developer: String,
    val ageRating: Int,
    val sizeMb: Float,
)