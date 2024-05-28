package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.model.Industry

interface FiltrationRepository {
    fun getFilter(): Filter?
    fun clearFilter()

    fun updateIndustry(industry: Industry)
    fun clearIndustry()
}
