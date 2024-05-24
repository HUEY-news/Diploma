package ru.practicum.android.diploma.filter.presentation.industry.model

import ru.practicum.android.diploma.filter.domain.model.Industry

sealed interface IndustriesState {
    object Loading : IndustriesState

    data class Content(
        val industries: ArrayList<Industry>,
    ) : IndustriesState

    data class Error(
        val errorMessage: String,
    ) : IndustriesState
}
