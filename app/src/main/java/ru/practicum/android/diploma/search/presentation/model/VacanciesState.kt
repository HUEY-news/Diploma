package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

sealed interface VacanciesState {
    object Loading : VacanciesState

    data class Content(
        val tracks: ArrayList<SimpleVacancy>,
    ) : VacanciesState

    data class Error(
        val errorMessage: String,
    ) : VacanciesState

    data class Empty(
        val message: String,
    ) : VacanciesState
}
