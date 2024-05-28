package ru.practicum.android.diploma.filter.data.impl

import com.google.gson.Gson
import ru.practicum.android.diploma.filter.data.api.FiltrationStorage
import ru.practicum.android.diploma.filter.domain.api.FiltrationRepository
import ru.practicum.android.diploma.filter.domain.model.Filter

class FiltrationRepositoryImpl(
    private val storage: FiltrationStorage,
    private val gson: Gson
) : FiltrationRepository {

    override fun getFilter(): Filter? {
        val filter = storage.getFilter()
        return if (filter.isEmpty() || filter == "null") {
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
}
