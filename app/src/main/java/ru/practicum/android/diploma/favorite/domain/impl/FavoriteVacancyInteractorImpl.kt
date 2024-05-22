package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

class FavoriteVacancyInteractorImpl(
    private val repository: FavoriteVacancyRepository
) : FavoriteVacancyInteractor {

    override suspend fun addVacancyToFavoriteList(vacancy: Vacancy) {
        repository.addVacancyToFavoriteList(vacancy)
    }

    override suspend fun removeVacancyFromFavoriteList(vacancy: Vacancy) {
        repository.removeVacancyFromFavoriteList(vacancy)
    }

    override suspend fun getVacancyFromFavoriteList(id: String): Vacancy {
        return repository.getVacancyFromFavoriteList(id)
    }

    override fun getAllVacancies(): Flow<List<SimpleVacancy>> {
        return repository.getAllFavoriteVacancies()
    }

    override suspend fun isVacancyFavorite(id: String): Boolean {
        return repository.isVacancyFavorite(id)
    }
}
