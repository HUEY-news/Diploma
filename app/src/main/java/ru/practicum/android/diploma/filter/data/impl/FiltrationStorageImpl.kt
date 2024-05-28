package ru.practicum.android.diploma.filter.data.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.filter.data.api.FiltrationStorage

class FiltrationStorageImpl(
    private val prefs: SharedPreferences
) : FiltrationStorage {

    override fun getFilter(): String = prefs.getString(FILTER, "") ?: ""
    override fun editFilter(editedFilter: String) { prefs.edit().putString(FILTER, editedFilter).apply() }
    override fun clearFilter() { prefs.edit().putString(FILTER, "").apply() }

    companion object {
        private const val FILTER = "FILTER"
    }
}
