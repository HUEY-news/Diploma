package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Vacancy

interface VacancyInteractor {
    fun getAllVacancies(): Flow<List<Vacancy>>
}
