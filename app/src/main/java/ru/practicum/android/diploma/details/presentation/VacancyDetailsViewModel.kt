package ru.practicum.android.diploma.details.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.api.SearchDetailsInteractor
import ru.practicum.android.diploma.details.presentation.model.StateLoadVacancy
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor

class VacancyDetailsViewModel(
    private val vacancyDetailsInteractor: SearchDetailsInteractor,
    private val resourceInteractor: ResourceInteractor
) : ViewModel() {
    private val vacancyLiveData = MutableLiveData<StateLoadVacancy>()
    fun observeVacancy(): MutableLiveData<StateLoadVacancy> = vacancyLiveData
    fun searchRequest(id: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor
                .searchVacancy(id)
                .collect { pair ->
                    if (pair.first != null) {
                        vacancyLiveData.postValue(
                            StateLoadVacancy.Content(
                                pair.first!!
                            )
                        )
                    }
                    when {
                        pair.second != null -> {
                            vacancyLiveData.postValue(
                                StateLoadVacancy.Error(
                                    errorMessage = resourceInteractor.getErrorInternetConnection()
                                ),
                            )
                        }
                    }
                }
        }
    }
}
