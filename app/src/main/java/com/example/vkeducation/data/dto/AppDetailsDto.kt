package com.example.vkeducation.data.dto

/**
 * DTO подробной информации о приложении (GET /{id}).
 * Пока описаны только поля, которые есть в каталоге; когда понадобятся
 * дополнительные поля из ответа сервера, их нужно добавить сюда (nullable).
 */
data class AppDetailsDto(
    val id: String?,
    val name: String?,
    val description: String?,
    val category: String?,
    val iconUrl: String?,
)
