package ru.practicum.android.diploma.search.data.network

import ru.practicum.android.diploma.filter.data.dto.SearchAreasResponse
import ru.practicum.android.diploma.filter.data.model.IndustryDto
import ru.practicum.android.diploma.search.data.dto.Response
import ru.practicum.android.diploma.util.Resource

interface NetworkClient {
    suspend fun doRequest(dto: Any, options: HashMap<String, String>): Response
    suspend fun doRequestDetails(dto: Any): Response

    suspend fun doRequestIndustries(): Resource<List<IndustryDto>>

    suspend fun doRequestAreas(): Resource<List<SearchAreasResponse>>
}
