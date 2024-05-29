package ru.practicum.android.diploma.favorite.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteVacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(entity: FavoriteVacancyEntity)

    @Delete
    suspend fun removeItem(entity: FavoriteVacancyEntity)

    @Query("SELECT * FROM vacancy_table")
    suspend fun getAllItems(): List<FavoriteVacancyEntity>

    @Query("SELECT * FROM vacancy_table WHERE id = :id")
    suspend fun getItem(id: Int): FavoriteVacancyEntity

    @Query("SELECT EXISTS(SELECT 1 FROM vacancy_table WHERE id = :id)")
    suspend fun isItemExists(id: Int): Boolean
}
