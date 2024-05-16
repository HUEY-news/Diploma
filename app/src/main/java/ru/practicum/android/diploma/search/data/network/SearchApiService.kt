package ru.practicum.android.diploma.search.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface SearchApiService {
    @GET("/search?entity=vacancy")
    suspend fun searchVacancy(
        @Query("text") text: String
    ): SearchResponse
}
