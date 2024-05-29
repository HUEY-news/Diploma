package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.domain.api.SearchDetailsInteractor
import ru.practicum.android.diploma.details.domain.impl.SearchDetailsInteractorImpl
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.domain.impl.FavoriteVacancyInteractorImpl
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor
import ru.practicum.android.diploma.sharing.domain.impl.ResourceInteractorImpl
import ru.practicum.android.diploma.sharing.domain.impl.SharingInteractorImpl

val interactorModule = module {
    factory<SearchInteractor> { SearchInteractorImpl(repository = get()) }
    factory<SearchDetailsInteractor> { SearchDetailsInteractorImpl(repository = get()) }
    factory<FavoriteVacancyInteractor> { FavoriteVacancyInteractorImpl(repository = get()) }
    single<ResourceInteractor> {
        ResourceInteractorImpl(get())
    }
    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
}
