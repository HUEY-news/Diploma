package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.convertor.DbConverter
import ru.practicum.android.diploma.details.data.impl.SearchDetailsRepositoryImpl
import ru.practicum.android.diploma.details.domain.api.SearchDetailsRepository
import ru.practicum.android.diploma.favorite.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.api.VacancyRepository
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.sharing.data.ExternalNavigator
import ru.practicum.android.diploma.sharing.data.ResourceProvider

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(context = androidContext(), client = get()) }
    single<SearchDetailsRepository> { SearchDetailsRepositoryImpl(client = get(), resourceProvider = get()) }
    single<VacancyRepository> { VacancyRepositoryImpl(appDatabase = get(), dbConverter = get()) }
    factory { DbConverter() }
    single<ExternalNavigator> {
        ExternalNavigator(context = androidContext())
    }
    single<ResourceProvider> {
        ResourceProvider(context = androidContext())
    }
}
