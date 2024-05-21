package ru.practicum.android.diploma.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.presentation.model.FavoriteScreenState

class FavoritesViewModel(
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel() {
    private val screenState = MutableLiveData<FavoriteScreenState>()
    fun observeScreenState(): LiveData<FavoriteScreenState> = screenState
    private fun renderState(state: FavoriteScreenState) { screenState.postValue(state) }

    fun updateData() {
        renderState(FavoriteScreenState.Loading)
        viewModelScope.launch {
            try {
                favoriteInteractor.getAllVacancies().collect { data ->
                    if (data.isEmpty()) renderState(FavoriteScreenState.Empty)
                    else renderState(FavoriteScreenState.Content(data))
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                renderState(FavoriteScreenState.Error)
            }
        }
    }
}
