package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesInteractor
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesRepository
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.util.Resource

class SearchIndustriesInteractorImpl(private val repository: SearchIndustriesRepository) : SearchIndustriesInteractor {
    override suspend fun searchIndustries(): Flow<Pair<List<Industry>?, String?>> {
        return repository.searchIndustries().map {
            when (it) {
                is Resource.Success -> Pair(it.data, null)
                is Resource.Error -> Pair(null, it.message)
            }
        }
    }

}
