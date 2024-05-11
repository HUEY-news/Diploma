package ru.practicum.android.diploma.di

import com.practicum.playlistmaker.convertor.DbConvertor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.VacancyRepository
import ru.practicum.android.diploma.search.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(context = androidContext(), client = get()) }
    single<VacancyRepository> { VacancyRepositoryImpl(appDatabase = get(), dbConvertor = get()) }
    factory { DbConvertor() }
}
