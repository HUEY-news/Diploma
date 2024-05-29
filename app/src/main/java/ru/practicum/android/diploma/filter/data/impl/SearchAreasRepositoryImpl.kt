package ru.practicum.android.diploma.filter.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.filter.data.dto.SearchAreasResponse
import ru.practicum.android.diploma.filter.data.model.AreaDto
import ru.practicum.android.diploma.filter.domain.api.SearchAreasRepository
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.util.Resource

class SearchAreasRepositoryImpl(private val client: NetworkClient) : SearchAreasRepository {
    override suspend fun searchAreas(): Flow<Resource<List<Country>>> = flow {
        val response = client.doRequestAreas()
        if (response.data != null) {
            emit(handleSuccessResponse(response.data))
        } else {
            response.message?.let { handleErrorResponse(it) }?.let { emit(it) }
        }
    }

    override suspend fun searchRegion(countryName: String): Flow<Resource<List<Area>>> = flow {
        val responses = client.doRequestAreas()
        if (responses.data != null) {
            responses.data.forEach { response ->
                if (response.name.equals(countryName)) {
                    emit(handleSuccessResponseRegion(response))
                }
            }
        } else {
            responses.message?.let { handleErrorResponseRegion(it) }?.let { emit(it) }
        }
    }

    private fun handleSuccessResponseRegion(response: SearchAreasResponse): Resource<List<Area>> {
        var areaList = emptyList<Area>()
        if (response.areas != null) {
            areaList = response.areas.map { createAreaFromResponse(it) }
        }
        return Resource.Success(areaList)
    }

    private fun handleErrorResponseRegion(message: String): Resource<List<Area>> {
        return Resource.Error(message)
    }

    private fun handleErrorResponse(message: String): Resource<List<Country>> {
        return Resource.Error(message)
    }

    private fun handleSuccessResponse(response: List<SearchAreasResponse>): Resource.Success<List<Country>> {
        val countryList = response.map { country ->
            Country(
                name = country.name,
                id = country.id,
                areas = country.areas?.map { createAreaFromResponse(it) },
                parentId = country.parentId
            )
        }
        return Resource.Success(countryList)
    }

    private fun createAreaFromResponse(area: AreaDto?): Area {
        return Area(
            id = area?.id,
            name = area?.name,
            parentId = area?.parentId
        )
    }
}
