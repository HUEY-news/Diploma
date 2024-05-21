package ru.practicum.android.diploma.favorite.domain.model

import ru.practicum.android.diploma.details.domain.model.Contacts
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Salary

data class FavoriteVacancy(
    val id: String,
    val address: String?,
    val alternateUrl: String?,
    val contacts: Contacts?,
    val description: String?,
    val employer: Employer?,
    val experience: String?,
    val keySkills: List<String?>?,
    val name: String,
    val professionalRoles: List<String?>?,
    val salary: Salary?,
    val schedule: String?,
)
