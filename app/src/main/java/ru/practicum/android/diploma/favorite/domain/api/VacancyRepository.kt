package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

interface VacancyRepository {
    fun getAllVacancies(): Flow<List<FavoriteVacancy>>
}
