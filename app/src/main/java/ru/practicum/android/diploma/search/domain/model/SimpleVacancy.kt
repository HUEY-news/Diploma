package ru.practicum.android.diploma.search.domain.model

import ru.practicum.android.diploma.details.domain.model.Employer
import ru.practicum.android.diploma.details.domain.model.Salary

data class SimpleVacancy(
    val id: String,
    val name: String,
    val address: String?,
    val employer: Employer?,
    val salary: Salary?,
    val found: Long
)
