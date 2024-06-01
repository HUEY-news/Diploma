package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.Industry

interface SearchIndustriesInteractor {
    suspend fun searchIndustries(): Flow<Pair<List<Industry>?, String?>>
}
