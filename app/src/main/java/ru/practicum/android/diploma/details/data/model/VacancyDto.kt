package ru.practicum.android.diploma.details.data.model

data class VacancyDto(
    val address: AddressDto?,
    val alternateUrl: String,
    val contacts: ContactsDto?,
    val description: String?,
    val employer: EmployerDto?,
    val experience: String?,
    val keySkills: List<String>?,
    val name: String?,
    val professionalRoles: List<String>?,
    val salary: SalaryDto?,
    val schedule: String?,
)
