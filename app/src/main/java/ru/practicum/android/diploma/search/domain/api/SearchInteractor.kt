package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

interface SearchInteractor {
    fun searchVacancy(expression: String, options: HashMap<String, String>): Flow<Pair<List<SimpleVacancy>?, String?>>
}
