package ru.practicum.android.diploma

import ru.practicum.android.diploma.db.VacancyDao

interface DatabaseClient {
    fun vacancyDao(): VacancyDao
}
