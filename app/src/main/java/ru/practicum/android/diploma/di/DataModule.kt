package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import androidx.room.Room
import ru.practicum.android.diploma.db.AppDatabase
import ru.practicum.android.diploma.DatabaseClient
import ru.practicum.android.diploma.db.VacancyDao

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "vacancy_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<AppDatabase>().vacancyDao() }
    single<DatabaseClient> { object : DatabaseClient {
        override fun vacancyDao(): VacancyDao = get()
        }
    }
}
