package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country

interface SearchAreasInteractor {
    suspend fun searchAreas(): Flow<Pair<List<Country>?, String?>>
    suspend fun searchRegionsByCountry(countryName: String): Flow<Pair<List<Area>?, String?>>

    suspend fun searchAllRegions(): Flow<Pair<List<Area>?, String?>>
}
