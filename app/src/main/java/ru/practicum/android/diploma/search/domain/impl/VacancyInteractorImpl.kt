package ru.practicum.android.diploma.search.domain.impl

import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.api.VacancyRepository
import kotlinx.coroutines.flow.Flow

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {
    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return repository.getAllVacancies()
    }
}
