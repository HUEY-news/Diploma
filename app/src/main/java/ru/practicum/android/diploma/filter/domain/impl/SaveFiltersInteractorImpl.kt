package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.SaveFiltersInteractor
import ru.practicum.android.diploma.filter.domain.api.SaveFiltersRepository
import ru.practicum.android.diploma.filter.domain.model.FiltersSave

class SaveFiltersInteractorImpl(
    private val saveFiltersRepository: SaveFiltersRepository
) : SaveFiltersInteractor {
    override fun getFilters(): FiltersSave {
        return saveFiltersRepository.getFilters()
    }

    override fun saveFilters(filters: FiltersSave) {
        saveFiltersRepository.saveFilters(filters)
    }
}
