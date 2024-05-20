package ru.practicum.android.diploma.details.presentation.model

import ru.practicum.android.diploma.details.domain.model.Vacancy

sealed interface StateLoadVacancy {
    object Loading : StateLoadVacancy
    data class Content(
        val vacancies: Vacancy,
    ) : StateLoadVacancy

    data class Error(
        val errorMessage: String,
    ) : StateLoadVacancy
}
