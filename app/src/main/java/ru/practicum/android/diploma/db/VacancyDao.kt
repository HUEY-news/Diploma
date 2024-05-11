package ru.practicum.android.diploma.db

import androidx.room.Dao
import androidx.room.Query


@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancy")
    fun getAllVacancies(): List<Vacancy>

}
