package com.example.vkeducation.domain.usecase

import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.repository.AppRepository

class GetAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): List<AppInfo> = repository.getApps()
}
