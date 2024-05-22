package ru.practicum.android.diploma.details.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.api.SearchDetailsInteractor
import ru.practicum.android.diploma.details.domain.model.Vacancy
import ru.practicum.android.diploma.details.presentation.model.StateLoadVacancy
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.sharing.domain.api.SharingInteractor

class VacancyDetailsViewModel(
    private val vacancyDetailsInteractor: SearchDetailsInteractor,
    private val resourceInteractor: ResourceInteractor,
    private val sharingInteractor: SharingInteractor,
    private val favoriteInteractor: FavoriteVacancyInteractor
) : ViewModel() {

    private var currentVacancy: Vacancy? = null
    private var isFavorite: Boolean = false

    private val vacancyLiveData = MutableLiveData<StateLoadVacancy>()
    fun observeVacancy(): MutableLiveData<StateLoadVacancy> = vacancyLiveData
    private fun renderState(state: StateLoadVacancy) { vacancyLiveData.postValue(state) }

    fun searchRequest(id: String) {
        renderState(StateLoadVacancy.Loading)
        viewModelScope.launch {

            isFavorite = favoriteInteractor.isVacancyFavorite(id)

            if (isFavorite) {
                currentVacancy = favoriteInteractor.getVacancyFromFavoriteList(id)
                renderState(StateLoadVacancy.Content(currentVacancy!!, isFavorite))
            } else {
                vacancyDetailsInteractor
                    .searchVacancy(id)
                    .collect { pair ->
                        if (pair.first != null) {
                            renderState(StateLoadVacancy.Content(pair.first!!, isFavorite))
                        }
                        when {
                            pair.second != null -> {
                                renderState(StateLoadVacancy.Error(resourceInteractor.getErrorInternetConnection()))
                            }
                        }
                    }
            }
        }
    }

    fun callPhoneNumber(phone: String) {
        sharingInteractor.callPhoneNumber(phone)
    }

    fun writeToEmployer(mail: String) {
        sharingInteractor.writeToEmployer(mail)
    }

    fun shareVacancy(url: String) {
        sharingInteractor.shareVacancy(url)
    }

    fun onFavoriteClicked() {
        currentVacancy?.let {
            if (isFavorite) removeVacancy(currentVacancy!!)
            else addVacancy(currentVacancy!!)
        }
    }

    private fun removeVacancy(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.removeVacancyFromFavoriteList(vacancy)
            renderState(StateLoadVacancy.Content(vacancy, false))
            isFavorite = false
        }
    }

    private fun addVacancy(vacancy: Vacancy) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteInteractor.addVacancyToFavoriteList(vacancy)
            renderState(StateLoadVacancy.Content(vacancy, true))
        }
    }
}
