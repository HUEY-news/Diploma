package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.details.ui.DetailsViewModel
import ru.practicum.android.diploma.favorite.ui.FavoritesViewModel
import ru.practicum.android.diploma.filter.ui.CountryViewModel
import ru.practicum.android.diploma.filter.ui.FiltrationViewModel
import ru.practicum.android.diploma.filter.ui.PlaceOfWorkViewModel
import ru.practicum.android.diploma.filter.ui.RegionViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModel

val viewModelModule = module {
    viewModel { DetailsViewModel() }
    viewModel { FavoritesViewModel() }
    viewModel { CountryViewModel() }
    viewModel { FiltrationViewModel() }
    viewModel { PlaceOfWorkViewModel() }
    viewModel { RegionViewModel() }
    viewModel { SearchViewModel() }
}
