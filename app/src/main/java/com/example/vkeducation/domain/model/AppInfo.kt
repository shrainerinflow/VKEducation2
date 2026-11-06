package com.example.vkeducation.domain.repository

import com.example.vkeducation.domain.model.AppInfo

/** Интерфейс репозитория: объявлен в domain, реализован в data. */
interface AppRepository {
    /** Каталог приложений с сервера. */
    suspend fun getApps(): List<AppInfo>
    suspend fun getShowcaseApps(): List<AppInfo>
    suspend fun getApp(id: String): AppInfo?

    /** Запрос подробной информации по id (GET /{id}). Пока нигде не используется в UI. */
    suspend fun getAppDetails(id: String): AppInfo?
}
