package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.domain.api.FiltrationRepository
import ru.practicum.android.diploma.filter.domain.model.Filter

class FiltrationInteractorImpl(
    private val repository: FiltrationRepository
) : FiltrationInteractor {
    override fun getFilter(): Filter? = repository.getFilter()
    override fun clearFilter() { repository.clearFilter() }
}
