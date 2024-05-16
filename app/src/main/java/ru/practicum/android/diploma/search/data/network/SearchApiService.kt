package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface SearchApiService {
    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: Работа для Федота",
        allowUnsafeNonAsciiValues = true
    )
    @GET("/vacancies?entity=vacancy")
    suspend fun searchVacancy(
        @Query("searchRequest") searchRequest: String,
        @QueryMap options: Map<String, String>,
    ): SearchResponse

    companion object {
        const val TOKEN = BuildConfig.HH_ACCESS_TOKEN
    }
}

