package com.example.vkeducation.data.mapper

import com.example.vkeducation.data.dto.AppDetailsDto
import com.example.vkeducation.data.dto.AppDto
import com.example.vkeducation.domain.model.AppInfo
import javax.inject.Inject

/** Конвертирует DTO → доменная модель. Некорректные записи (без id или названия) отбрасываются. */
class AppMapper @Inject constructor() {

    fun toDomain(dto: AppDto): AppInfo? = build(dto.id, dto.name, dto.description, dto.category, dto.iconUrl)

    fun toDomain(dto: AppDetailsDto): AppInfo? =
        build(dto.id, dto.name, dto.description, dto.category, dto.iconUrl)

    fun toDomainList(dtos: List<AppDto>): List<AppInfo> = dtos.mapNotNull { toDomain(it) }

    private fun build(
        id: String?,
        name: String?,
        description: String?,
        category: String?,
        iconUrl: String?,
    ): AppInfo? {
        if (id == null || name == null) return null
        return AppInfo(
            id = id,
            name = name,
            description = description.orEmpty(),
            category = category ?: "Без категории",
            iconUrl = iconUrl,
        )
    }
}
