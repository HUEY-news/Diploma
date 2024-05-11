package ru.practicum.android.diploma.search.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllVacancies(): List<VacancyEntity>
}
