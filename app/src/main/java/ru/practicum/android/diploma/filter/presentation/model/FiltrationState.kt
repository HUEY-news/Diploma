package ru.practicum.android.diploma.filter.presentation.model

sealed interface FiltrationState  {

    object NoFilters : FiltrationState

    data class FiltersContent(
        val workPlace: String,
        val industry: String,
        val salary: String,
    ) : FiltrationState
}
