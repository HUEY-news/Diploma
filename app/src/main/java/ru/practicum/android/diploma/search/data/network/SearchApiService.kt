package ru.practicum.android.diploma.search.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.details.data.dto.SearchDetailsResponse
import ru.practicum.android.diploma.filter.data.dto.SearchAreasResponse
import ru.practicum.android.diploma.filter.data.dto.SearchIndustriesResponse
import ru.practicum.android.diploma.search.data.dto.SearchResponse

interface SearchApiService {
    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: Работа для Федота",
        allowUnsafeNonAsciiValues = true
    )
    @GET("/vacancies?entity=vacancy")
    suspend fun searchVacancy(
        @Query("text") searchRequest: String,
        @QueryMap options: Map<String, String>,
    ): SearchResponse

    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: Работа для Федота",
        allowUnsafeNonAsciiValues = true
    )
    @GET("/vacancies/{vacancy_id}")
    suspend fun searchVacancyDetails(
        @Path("vacancy_id") searchRequest: String,
    ): SearchDetailsResponse

    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: Работа для Федота",
        allowUnsafeNonAsciiValues = true
    )
    @GET("/industries")
    suspend fun searchIndustries(): Response<List<SearchIndustriesResponse>>

    @Headers(
        "Authorization: Bearer $TOKEN",
        "HH-User-Agent: Работа для Федота",
        allowUnsafeNonAsciiValues = true
    )
    @GET("/areas")
    suspend fun searchAreas(): Response<List<SearchAreasResponse>>

    companion object {
        const val TOKEN = BuildConfig.HH_ACCESS_TOKEN
    }
}

