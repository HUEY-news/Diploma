package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.util.Resource

interface SearchIndustriesRepository {
    suspend fun searchIndustries(): Flow<Resource<List<Industry>>>
}
