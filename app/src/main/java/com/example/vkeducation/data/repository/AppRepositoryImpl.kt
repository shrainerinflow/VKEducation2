package com.example.vkeducation.data.repository

import com.example.vkeducation.data.mapper.AppMapper
import com.example.vkeducation.data.remote.CatalogApi
import com.example.vkeducation.domain.model.AppInfo
import com.example.vkeducation.domain.repository.AppRepository
import javax.inject.Inject

/** Реализация репозитория: берёт DTO с сервера и отдаёт наружу уже доменные модели. */
class AppRepositoryImpl @Inject constructor(
    private val api: CatalogApi,
    private val mapper: AppMapper,
) : AppRepository {

    // Кэш последнего каталога, чтобы карточка и «Витрина» не дёргали сервер повторно.
    // Репозиторий singleton (см. RepositoryModule), поэтому кэш живёт, пока живо приложение.
    @Volatile
    private var cache: List<AppInfo>? = null

    /** Всегда идёт на сервер (так работает и кнопка «Повторить»). */
    override suspend fun getApps(): List<AppInfo> =
        mapper.toDomainList(api.getCatalog()).also { cache = it }

    override suspend fun getShowcaseApps(): List<AppInfo> =
        (cache ?: getApps()).take(SHOWCASE_SIZE)

    override suspend fun getApp(id: String): AppInfo? =
        (cache ?: getApps()).firstOrNull { it.id == id }

    override suspend fun getAppDetails(id: String): AppInfo? =
        mapper.toDomain(api.getAppDetails(id))

    private companion object {
        const val SHOWCASE_SIZE = 9 // 3 страницы по 3
    }
}
