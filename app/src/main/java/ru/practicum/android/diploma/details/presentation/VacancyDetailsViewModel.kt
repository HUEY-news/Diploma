package ru.practicum.android.diploma.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.api.SearchDetailsInteractor
import ru.practicum.android.diploma.details.domain.model.Vacancy

class VacancyDetailsViewModel(private val vacancyDetailsInteractor: SearchDetailsInteractor) : ViewModel() {
    private lateinit var vacancy: Vacancy
    fun searchRequest(id: String) {
        viewModelScope.launch {
            vacancyDetailsInteractor
                .searchVacancy(id)
                .collect { pair ->
                    if (pair.first != null) {
                        vacancy = pair.first!!
                    }
                    when {
                        pair.second != null -> {
                            val error = pair.second
                        }
                    }
                }
        }
    }
}
