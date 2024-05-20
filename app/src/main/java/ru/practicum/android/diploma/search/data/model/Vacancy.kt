package ru.practicum.android.diploma.search.data.model

import ru.practicum.android.diploma.search.domain.model.Phones

data class Vacancy(
    val id: String,
    val name: String,
    val city: String,
    val employer: String,
    val employerLogoUrls: String?,
    val currency: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val experience: String,
    val employmentType: String,
    val schedule: String,
    val description: String,
    val keySkills: List<String>,
    val phone: List<Phones>?,
    val email: String?,
    val contactPerson: String?,
    val url: String
)
