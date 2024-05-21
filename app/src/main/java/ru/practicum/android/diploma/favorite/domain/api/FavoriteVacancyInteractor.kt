package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

interface FavoriteVacancyInteractor {

    suspend fun addVacancyToFavoriteList(vacancy: FavoriteVacancy)
    suspend fun removeVacancyFromFavoriteList(vacancy: FavoriteVacancy)

    fun getVacancyFromFavoriteList(id: Int): Flow<FavoriteVacancy>
    fun getAllVacancies(): Flow<List<FavoriteVacancy>>

    fun isVacancyFavorite(id: Int): Flow<Boolean>
}
