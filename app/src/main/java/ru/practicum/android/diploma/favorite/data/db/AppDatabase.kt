package ru.practicum.android.diploma.favorite.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.convertor.TypeConverter

@Database(version = 1, entities = [FavoriteVacancyEntity::class])
@TypeConverters(TypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteVacancyDao(): FavoriteVacancyDao
}
