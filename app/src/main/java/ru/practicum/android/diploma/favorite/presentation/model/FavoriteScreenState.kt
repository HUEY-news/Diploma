package ru.practicum.android.diploma.favorite.presentation.model

import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

sealed interface FavoriteScreenState {
    object Loading: FavoriteScreenState
    data class Content(val data: List<FavoriteVacancy>): FavoriteScreenState
    data class Error(val message: String): FavoriteScreenState
    data class Empty(val message: String): FavoriteScreenState
}
