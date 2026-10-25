package com.example.vkeducation.domain.usecase

import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.repository.AppRepository

class GetAppUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(id: Int): AppInfo? = repository.getApp(id)
}
