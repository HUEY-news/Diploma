package ru.practicum.android.diploma.filter.data.api

interface FiltrationStorage {
    fun getFilter(): String
    fun editFilter(editedFilter: String)
    fun clearFilter()
}
