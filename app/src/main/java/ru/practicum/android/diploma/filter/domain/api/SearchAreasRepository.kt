package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.util.Resource

interface SearchAreasRepository {
    suspend fun searchAreas(): Flow<Resource<List<Country>>>

    suspend fun searchRegion(countryName: String): Flow<Resource<List<Area>>>

    suspend fun searchAllRegions(): Flow<Resource<List<Area>>>
}
