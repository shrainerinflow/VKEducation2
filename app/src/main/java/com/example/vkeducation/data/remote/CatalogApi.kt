package com.example.vkeducation.data.remote

import com.example.vkeducation.data.dto.AppDetailsDto
import com.example.vkeducation.data.dto.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

/** Retrofit-интерфейс сервера. Базовый URL задан в NetworkModule. */
interface CatalogApi {

    /** GET http://185.103.109.134/catalog */
    @GET("catalog")
    suspend fun getCatalog(): List<AppDto>

    /** GET http://185.103.109.134/{id} */
    @GET("{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto
}
