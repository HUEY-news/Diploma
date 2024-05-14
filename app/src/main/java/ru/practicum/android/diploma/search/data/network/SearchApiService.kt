package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface SearchApiService {
    @Headers(
        "Authorization: Bearer YOUR_TOKEN",
        "HH-User-Agent: Application Name (Работа для Федота)"
    )
    @GET("/vacancies?entity=vacancy")
    suspend fun searchVacancy(
        @Query("searchRequest") searchRequest: String,
        @QueryMap options: Map<String, String>?,
    ): SearchResponse
}
