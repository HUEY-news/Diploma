package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

interface FavoriteVacancyInteractor {

    suspend fun addVacancyToFavoriteList(vacancy: Vacancy)
    suspend fun removeVacancyFromFavoriteList(vacancy: Vacancy)

    suspend fun getVacancyFromFavoriteList(id: String): Vacancy
    fun getAllVacancies(): Flow<List<SimpleVacancy>>

    suspend fun isVacancyFavorite(id: String): Boolean
}
