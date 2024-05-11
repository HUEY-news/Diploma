package ru.practicum.android.diploma.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy")
data class Vacancy(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String
)
