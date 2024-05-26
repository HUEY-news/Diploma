package ru.practicum.android.diploma.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.filter.domain.api.SearchAreasInteractor
import ru.practicum.android.diploma.filter.domain.api.SearchAreasRepository
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.util.Resource

class SearchAreasInteractorImpl(private val repository: SearchAreasRepository) : SearchAreasInteractor {
    override suspend fun searchAreas(): Flow<Pair<List<Country>?, String?>> {
        return repository.searchAreas().map {
            when (it) {
                is Resource.Success -> Pair(it.data, null)
                is Resource.Error -> Pair(null, it.message)
            }
        }
    }
}
