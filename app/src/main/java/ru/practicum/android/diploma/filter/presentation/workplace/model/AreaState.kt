package ru.practicum.android.diploma.filter.presentation.workplace.model

interface AreaState {
    object Empty : AreaState
    data class CountryName(
        val country: String,
    ) : AreaState

    data class RegionName(
        val region: String,
    ) : AreaState

    data class FullArea(
        val country: String,
        val region: String,
    ) : AreaState

}
