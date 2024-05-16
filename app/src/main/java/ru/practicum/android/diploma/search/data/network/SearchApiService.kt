package ru.practicum.android.diploma.search.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.search.data.dto.SearchResponse


interface SearchApiService {
    fun foo(@Header("Accept-Language", allowUnsafeNonAsciiValues = true) lang: String?): Call<ResponseBody?>?

    @Headers(
        "Authorization: Bearer GH_HH_ACCESS_TOKEN",
        "HH-User-Agent: Application Name (Работа для Федота)"
    )
    @GET("/vacancies?entity=vacancy")
    suspend fun searchVacancy(
//        @Header("Accept-Language", allowUnsafeNonAsciiValues = true)
        @Query("searchRequest") searchRequest: String,
        @QueryMap options: Map<String, String>,
    ): SearchResponse
}
