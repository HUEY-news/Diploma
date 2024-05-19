package ru.practicum.android.diploma.details.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchDetailsRepository {
    suspend fun searchVacancy(expression: String): Flow<Resource<Vacancy>>
}
