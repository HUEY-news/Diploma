package ru.practicum.android.diploma.favorite.presentation

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor

class FavoritesViewModel(
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel()
