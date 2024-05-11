package ru.practicum.android.diploma.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Vacancy::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
