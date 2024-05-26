package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Country

interface SearchAreasInteractor {
    suspend fun searchAreas(): Flow<Pair<List<Country>?, String?>>
}
