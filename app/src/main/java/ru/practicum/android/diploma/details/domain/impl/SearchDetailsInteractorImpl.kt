package ru.practicum.android.diploma.details.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.details.domain.api.SearchDetailsInteractor
import ru.practicum.android.diploma.details.domain.api.SearchDetailsRepository
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchDetailsInteractorImpl(private val repository: SearchDetailsRepository) : SearchDetailsInteractor {
    override suspend fun searchVacancy(expression: String): Flow<Pair<Vacancy?, String?>> {
        return repository.searchVacancy(expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }
}
