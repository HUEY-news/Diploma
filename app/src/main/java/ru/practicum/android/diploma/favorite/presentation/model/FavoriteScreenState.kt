package ru.practicum.android.diploma.favorite.presentation.model

import ru.practicum.android.diploma.search.domain.model.SimpleVacancy

sealed interface FavoriteScreenState {
    object Loading : FavoriteScreenState
    data class Content(val data: List<SimpleVacancy>) : FavoriteScreenState
    object Error : FavoriteScreenState
    object Empty : FavoriteScreenState
}
