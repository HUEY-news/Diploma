package ru.practicum.android.diploma.filter.domain.impl

import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.filter.domain.api.FiltrationRepository
import ru.practicum.android.diploma.filter.domain.model.Area
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.model.Industry

class FiltrationInteractorImpl(
    private val repository: FiltrationRepository,
) : FiltrationInteractor {

    override fun getFilter(): Filter? = repository.getFilter()
    override fun checkFilter(filter: Filter) = repository.checkFilter(filter)

    override fun updateIndustry(industry: Industry) {
        repository.updateIndustry(industry)
    }

    override fun clearIndustry() {
        repository.clearIndustry()
    }

    override fun updateSalary(salary: String) {
        repository.updateSalary(salary)
    }

    override fun clearSalary() {
        repository.clearSalary()
    }

    override fun updateCheckBox(isChecked: Boolean) {
        repository.updateCheckBox(isChecked)
    }

    override fun updateCountry(country: Country) {
        repository.updateCountry(country)
    }

    override fun clearCountry() {
        repository.clearCountry()
    }

    override fun updateArea(area: Area) {
        repository.updateArea(area)
    }

    override fun clearArea() {
        repository.clearArea()
    }
}
