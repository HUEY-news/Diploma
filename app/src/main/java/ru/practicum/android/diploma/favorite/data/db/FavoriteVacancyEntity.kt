package ru.practicum.android.diploma.favorite.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.details.domain.model.Contacts
import ru.practicum.android.diploma.details.domain.model.Employer
import ru.practicum.android.diploma.details.domain.model.Salary

@Entity(tableName = "vacancy_table")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: Int,
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
    val addingTime: Long = 0,
    val area: String?
)
