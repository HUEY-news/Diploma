package ru.practicum.android.diploma.details.data.dto

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.details.data.model.AddressDto
import ru.practicum.android.diploma.details.data.model.ContactsDto
import ru.practicum.android.diploma.details.data.model.EmployerDto
import ru.practicum.android.diploma.details.data.model.ExperienceDto
import ru.practicum.android.diploma.details.data.model.KeySkillDto
import ru.practicum.android.diploma.details.data.model.ProfessionalRoleDto
import ru.practicum.android.diploma.details.data.model.SalaryDto
import ru.practicum.android.diploma.details.data.model.ScheduleDto
import ru.practicum.android.diploma.search.data.dto.Response

data class SearchDetailsResponse(
    @SerializedName("address") val address: AddressDto?,
    @SerializedName("alternateUrl") val alternateUrl: String?,
    @SerializedName("contacts") val contacts: ContactsDto?,
    @SerializedName("description") val description: String?,
    @SerializedName("employer") val employer: EmployerDto?,
    @SerializedName("experience") val experience: ExperienceDto?,
    @SerializedName("keySkills") val keySkills: List<KeySkillDto>?,
    @SerializedName("name") val name: String?,
    @SerializedName("professionalRoles") val professionalRoles: List<ProfessionalRoleDto>?,
    @SerializedName("salary") val salary: SalaryDto?,
    @SerializedName("schedule") val schedule: ScheduleDto?,
) : Response()
