package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.main_search.data.SearchRepository
import ru.practicum.android.diploma.db.VacancyDetailsRepository
import ru.practicum.android.diploma.db.VacancyRepository

val repositoryModule = module {
    single { SearchRepository(get()) }
    single { VacancyDetailsRepository(get()) }
    single { VacancyRepository(get()) }
}

