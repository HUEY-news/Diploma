package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

class FavoriteVacancyInteractorImpl(
    private val repository: FavoriteVacancyRepository
) : FavoriteVacancyInteractor {

    override suspend fun addVacancyToFavoriteList(vacancy: FavoriteVacancy) {
        repository.addVacancyToFavoriteList(vacancy)
    }

    override suspend fun removeVacancyFromFavoriteList(vacancy: FavoriteVacancy) {
        repository.removeVacancyFromFavoriteList(vacancy)
    }

    override fun getVacancyFromFavoriteList(id: Int): Flow<FavoriteVacancy> {
        return repository.getVacancyFromFavoriteList(id)
    }

    override fun getAllVacancies(): Flow<List<FavoriteVacancy>> {
        return repository.getAllFavoriteVacancies()
    }
}
