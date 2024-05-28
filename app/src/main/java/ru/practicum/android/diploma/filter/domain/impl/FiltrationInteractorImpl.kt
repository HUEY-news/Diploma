package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.domain.api.FiltrationRepository
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.model.Industry

class FiltrationInteractorImpl(
    private val repository: FiltrationRepository
) : FiltrationInteractor {
    override fun getFilter(): Filter? = repository.getFilter()
    override fun clearFilter() { repository.clearFilter() }

    override fun updateIndustry(industry: Industry) { repository.updateIndustry(industry) }
    override fun clearIndustry() { repository.clearIndustry() }
}
