package ru.practicum.android.diploma.details.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.model.Vacancy

interface SearchDetailsInteractor {
    suspend fun searchVacancy(expression: String): Flow<Pair<Vacancy?, String?>>
}
