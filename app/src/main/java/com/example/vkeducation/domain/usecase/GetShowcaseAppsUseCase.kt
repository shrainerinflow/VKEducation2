package com.example.vkeducation.domain.usecase

import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.repository.AppRepository
import javax.inject.Inject

class GetShowcaseAppsUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke(): List<AppInfo> = repository.getShowcaseApps()
}
