package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.api.VacancyRepository

class VacancyInteractorImpl(
    private val repository: VacancyRepository
) : VacancyInteractor {
    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return repository.getAllVacancies()
    }
}
