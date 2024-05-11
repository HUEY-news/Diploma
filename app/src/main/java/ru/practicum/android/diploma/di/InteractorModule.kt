package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.search.domain.impl.VacancyInteractorImpl

val interactorModule = module {
    factory<SearchInteractor> { SearchInteractorImpl(repository = get()) }
    factory<VacancyInteractor> { VacancyInteractorImpl(repository = get()) }
}
