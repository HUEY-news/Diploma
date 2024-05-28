package ru.practicum.android.diploma.filter.data.impl

import com.google.gson.Gson
import ru.practicum.android.diploma.filter.data.api.FiltrationStorage
import ru.practicum.android.diploma.filter.domain.api.FiltrationRepository
import ru.practicum.android.diploma.filter.domain.model.Filter
import ru.practicum.android.diploma.filter.domain.model.Industry

class FiltrationRepositoryImpl(
    private val storage: FiltrationStorage,
    private val gson: Gson
) : FiltrationRepository {

    override fun getFilter(): Filter? {
        val filter = storage.getFilter()
        return if (filter.isNullOrEmpty()) {
            null
        } else if (checkFilter(gson.fromJson(filter, Filter::class.java))) {
            null
        } else {
            gson.fromJson(filter, Filter::class.java)
        }
    }

    private fun checkFilter(filter: Filter): Boolean =
        with(filter) {
            !isOnlyWithSalary &&
                countryName == null &&
                regionName == null &&
                regionId == null &&
                industryName == null &&
                industryId == null &&
                expectedSalary == null
        }

    override fun clearFilter() { storage.clearFilter() }

    override fun updateIndustry(industry: Industry) {
        val filter = getFilter()
        val updatedFilter = filter?.copy(industryId = industry.id, industryName = industry.name)
            ?: Filter(industryId = industry.id, industryName = industry.name)
        storage.updateFilter(Gson().toJson(updatedFilter))
    }

    override fun clearIndustry() {
        val filter = getFilter()
        val clearedFilter = filter?.copy(industryName = null, industryId = null)
        storage.updateFilter(Gson().toJson(clearedFilter))
    }
}
