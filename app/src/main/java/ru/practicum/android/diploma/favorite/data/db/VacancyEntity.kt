package ru.practicum.android.diploma.favorite.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    val vacancyId: Int,
    val vacancyTitle: String,
    val vacancyDescription: String,
    val addingTime: Long = 0
)
