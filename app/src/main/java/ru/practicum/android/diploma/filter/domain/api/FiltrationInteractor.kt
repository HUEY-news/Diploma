package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.model.Filter

interface FiltrationInteractor {
    fun getFilter(): Filter?
    fun clearFilter()
}
