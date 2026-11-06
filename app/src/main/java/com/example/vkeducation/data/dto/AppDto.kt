package com.example.vkeducation.data.dto

/**
 * DTO элемента каталога (GET /catalog). Поля nullable, как у настоящего ответа сервера.
 * Наружу из data-слоя не уходит: перед этим конвертируется Mapper-ом в доменную модель.
 */
data class AppDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val category: String?,
    val iconUrl: String?,
)
