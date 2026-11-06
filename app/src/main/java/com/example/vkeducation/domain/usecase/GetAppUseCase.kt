package com.example.vkeducation.domain.usecase

import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.repository.AppRepository
import javax.inject.Inject

class GetAppUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(id: String): AppInfo? = repository.getApp(id)
}
