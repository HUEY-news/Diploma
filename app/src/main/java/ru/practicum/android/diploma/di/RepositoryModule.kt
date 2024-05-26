package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.convertor.DbConverter
import ru.practicum.android.diploma.details.data.impl.SearchDetailsRepositoryImpl
import ru.practicum.android.diploma.details.domain.api.SearchDetailsRepository
import ru.practicum.android.diploma.favorite.data.impl.FavoriteVacancyRepositoryImpl
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyRepository
import ru.practicum.android.diploma.filter.data.impl.SearchAreasRepositoryImpl
import ru.practicum.android.diploma.filter.data.impl.SearchIndustriesRepositoryImpl
import ru.practicum.android.diploma.filter.domain.api.SearchAreasRepository
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesRepository
import ru.practicum.android.diploma.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.search.domain.api.SearchRepository
import ru.practicum.android.diploma.sharing.data.ExternalNavigator
import ru.practicum.android.diploma.sharing.data.ResourceProvider

val repositoryModule = module {
    single<SearchRepository> { SearchRepositoryImpl(resourceProvider = get(), client = get()) }
    single<SearchDetailsRepository> { SearchDetailsRepositoryImpl(client = get(), resourceProvider = get()) }
    single<FavoriteVacancyRepository> { FavoriteVacancyRepositoryImpl(appDatabase = get(), dbConverter = get()) }
    factory { DbConverter() }
    single<ExternalNavigator> { ExternalNavigator(context = androidContext()) }
    single<ResourceProvider> { ResourceProvider(context = androidContext()) }
    single<SearchIndustriesRepository> { SearchIndustriesRepositoryImpl(client = get()) }
    single<SearchAreasRepository> { SearchAreasRepositoryImpl(client = get()) }
}
