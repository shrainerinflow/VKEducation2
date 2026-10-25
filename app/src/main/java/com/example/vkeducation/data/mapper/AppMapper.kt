package com.example.vkeducation.data.mapper

import com.example.vkeducation.data.dto.AppDto
import com.example.vkeducation.domain.model.AppInfo

class AppMapper {

    fun toDomain(dto: AppDto): AppInfo? {
        val id = dto.id ?: return null
        val name = dto.name ?: return null
        val description = dto.description.orEmpty()
        return AppInfo(
            id = id,
            name = name,
            description = description,
            fullDescription = dto.fullDescription ?: description,
            category = dto.category ?: "Без категории",
            developer = dto.developer ?: "Неизвестный разработчик",
            ageRating = dto.ageRating ?: 0,
            sizeMb = dto.sizeMb ?: 0f,
        )
    }

    fun toDomainList(dtos: List<AppDto>): List<AppInfo> = dtos.mapNotNull { toDomain(it) }
}