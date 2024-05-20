package ru.practicum.android.diploma.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorite.domain.api.VacancyInteractor
import ru.practicum.android.diploma.favorite.domain.api.VacancyRepository
import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {
    override fun getAllVacancies(): Flow<List<FavoriteVacancy>> {
        return repository.getAllVacancies()
    }
}
