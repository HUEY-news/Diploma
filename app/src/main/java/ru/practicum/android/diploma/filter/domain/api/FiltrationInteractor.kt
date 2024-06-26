package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.model.Industry

interface FiltrationInteractor {
    fun getFilter(): Filter?
    fun updateIndustry(industry: Industry)
    fun clearIndustry()
    fun updateSalary(salary: String)
    fun clearSalary()
    fun updateCheckBox(isChecked: Boolean)
    fun updateCountry(country: Country)
    fun clearCountry()
    fun updateArea(area: Area)
    fun clearArea()
}
