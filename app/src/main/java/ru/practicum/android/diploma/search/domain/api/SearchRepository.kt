package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    fun searchVacancy(expression: String, options: HashMap<String, String>?): Flow<Resource<List<SimpleVacancy>>>
}
