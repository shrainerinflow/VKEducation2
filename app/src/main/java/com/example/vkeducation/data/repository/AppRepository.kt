package com.example.vkeducation.data.repository

import com.example.vkeducation.data.mapper.AppMapper
import com.example.vkeducation.data.source.AppsLocalDataSource
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.repository.AppRepository
import javax.inject.Inject

/** Реализация репозитория: берёт DTO из источника и отдаёт наружу уже доменные модели. */
class AppRepositoryImpl @Inject constructor(
    private val dataSource: AppsLocalDataSource,
    private val mapper: AppMapper,
) : AppRepository {

    override suspend fun getApps(): List<AppInfo> =
        mapper.toDomainList(dataSource.getApps())

    override suspend fun getShowcaseApps(): List<AppInfo> =
        mapper.toDomainList(dataSource.getShowcase())

    override suspend fun getApp(id: Int): AppInfo? =
        (dataSource.getApps() + dataSource.getShowcase())
            .firstOrNull { it.id == id }
            ?.let { mapper.toDomain(it) }
}
