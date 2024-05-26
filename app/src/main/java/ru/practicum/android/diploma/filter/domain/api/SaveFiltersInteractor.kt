package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.model.FiltersSave

interface SaveFiltersInteractor {
    fun getFilters():FiltersSave
    fun saveFilters(filters: FiltersSave)

}
