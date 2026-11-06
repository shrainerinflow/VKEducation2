package com.example.vkeducation.domain.repository

import com.example.vkeducation.domain.model.AppInfo

/** Интерфейс репозитория: объявлен в domain, реализован в data. */
interface AppRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun getShowcaseApps(): List<AppInfo>
    suspend fun getApp(id: Int): AppInfo?
}