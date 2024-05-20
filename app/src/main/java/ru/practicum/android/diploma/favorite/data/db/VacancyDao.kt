package ru.practicum.android.diploma.favorite.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VacancyDao {
    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllItems(): List<VacancyEntity>

    @Query("SELECT * FROM vacancy_table WHERE id = :id")
    suspend fun getItem(id: Int): VacancyEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(vacancy: VacancyEntity)

    @Delete
    suspend fun removeItem(id: Int)
}
