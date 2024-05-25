package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.details.presentation.VacancyDetailsViewModel
import ru.practicum.android.diploma.favorite.presentation.FavoritesViewModel
import ru.practicum.android.diploma.filter.presentation.CountryViewModel
import ru.practicum.android.diploma.filter.presentation.FiltrationViewModel
import ru.practicum.android.diploma.filter.presentation.PlaceOfWorkViewModel
import ru.practicum.android.diploma.filter.presentation.RegionViewModel
import ru.practicum.android.diploma.filter.presentation.industry.IndustryViewModel
import ru.practicum.android.diploma.search.presentation.SearchViewModel

val viewModelModule = module {
    viewModel { VacancyDetailsViewModel(get(), get(), get(), favoriteInteractor = get()) }
    viewModel { FavoritesViewModel(favoriteInteractor = get()) }
    viewModel { CountryViewModel() }
    viewModel { FiltrationViewModel() }
    viewModel { PlaceOfWorkViewModel() }
    viewModel { RegionViewModel() }
    viewModel { IndustryViewModel(searchIndustriesInteractor = get()) }
    viewModel { SearchViewModel(resourceInteractor = get(), searchInteractor = get()) }
}
