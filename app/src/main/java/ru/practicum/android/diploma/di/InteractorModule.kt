package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.favorite.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.favorite.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.sharing.domain.impl.ResourceInteractorImpl

val interactorModule = module {
    factory<SearchInteractor> { SearchInteractorImpl(repository = get()) }
    factory<VacancyInteractor> { VacancyInteractorImpl(repository = get()) }
    single<ResourceInteractor> {
        ResourceInteractorImpl(get())
    }
}
