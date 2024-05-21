package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

interface FavoriteVacancyRepository {

    suspend fun addVacancyToFavoriteList(vacancy: FavoriteVacancy)
    suspend fun removeVacancyFromFavoriteList(vacancy: FavoriteVacancy)

    fun getVacancyFromFavoriteList(id: Int): Flow<FavoriteVacancy>
    fun getAllFavoriteVacancies(): Flow<List<SimpleVacancy>>

    fun isVacancyFavorite(id: Int): Flow<Boolean>
}
