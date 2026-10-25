package com.example.vkeducation.di

import com.example.vkeducation.data.mapper.AppMapper
import com.example.vkeducation.data.repository.AppRepositoryImpl
import com.example.vkeducation.data.source.AppsLocalDataSource
import com.example.vkeducation.domain.repository.AppRepository
import com.example.vkeducation.domain.usecase.GetAppUseCase
import com.example.vkeducation.domain.usecase.GetAppsUseCase
import com.example.vkeducation.domain.usecase.GetShowcaseAppsUseCase

/** Простейшая ручная сборка зависимостей (вместо Hilt/Koin). */
object AppContainer {
    private val repository: AppRepository by lazy {
        AppRepositoryImpl(AppsLocalDataSource(), AppMapper())
    }

    val getAppsUseCase: GetAppsUseCase by lazy { GetAppsUseCase(repository) }
    val getShowcaseAppsUseCase: GetShowcaseAppsUseCase by lazy { GetShowcaseAppsUseCase(repository) }
    val getAppUseCase: GetAppUseCase by lazy { GetAppUseCase(repository) }
}