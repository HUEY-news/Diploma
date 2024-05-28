package ru.practicum.android.diploma.filter.data.api

interface FiltrationStorage {
    fun getFilter(): String
    fun updateFilter(editedFilter: String)
    fun clearFilter()
}
