package ru.practicum.android.diploma.filter.presentation.country.model

import ru.practicum.android.diploma.filter.domain.model.Country

interface CountryState {
    object Loading : CountryState

    data class Content(
        val countries: ArrayList<Country>,
    ) : CountryState

    data class Error(
        val errorMessage: String,
    ) : CountryState
}
