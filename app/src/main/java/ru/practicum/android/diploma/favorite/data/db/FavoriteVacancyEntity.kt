package ru.practicum.android.diploma.favorite.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.favorite.data.model.PhonesDto
import ru.practicum.android.diploma.favorite.domain.model.Phones

@Entity(tableName = "vacancy_table")
data class FavoriteVacancyEntity(
    @PrimaryKey
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
    val phone: List<PhonesDto>?,
    val email: String?,
    val contactPerson: String?,
    val url: String,
    val addingTime: Long = 0
)
