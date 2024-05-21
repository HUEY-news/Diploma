package ru.practicum.android.diploma.favorite.presentation.model

import ru.practicum.android.diploma.favorite.domain.model.FavoriteVacancy

sealed interface FavoriteScreenState {
    object Loading: FavoriteScreenState
    data class Content(val data: List<FavoriteVacancy>): FavoriteScreenState
    object Error: FavoriteScreenState
    object Empty: FavoriteScreenState
}
