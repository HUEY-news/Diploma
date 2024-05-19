package ru.practicum.android.diploma.search.domain.model

data class SimpleVacancy(
    val id: String,
    val name: String,
    val address: String?,
    val employer: Employer?,
    val salary: Salary?,
    val found: Long,
)
