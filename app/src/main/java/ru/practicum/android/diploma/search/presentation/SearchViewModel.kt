package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val resourceInteractor: ResourceInteractor,
    private val searchInteractor: SearchInteractor,
) :
    ViewModel() {
    var lastText: String = ""

    private val stateLiveData = MutableLiveData<VacanciesState>()

    fun observeState(): LiveData<VacanciesState> = stateLiveData

    private val trackSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchRequest(changedText)
        }

    fun searchDebounce(changedText: String) {
        lastText = changedText
        trackSearchDebounce(changedText)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(VacanciesState.Loading)
            viewModelScope.launch {
                searchInteractor
                    .searchVacancy(newSearchText, null)
                    .collect { pair ->
                        val vacancies = ArrayList<SimpleVacancy>()
                        if (pair.first != null) {
                            vacancies.addAll(pair.first!!)
                        }

                        when {
                            pair.second != null -> {
                                renderState(
                                    VacanciesState.Error(
                                        errorMessage = resourceInteractor.getErrorInternetConnection()
                                    ),
                                )
                            }

                            vacancies.isEmpty() -> {
                                renderState(
                                    VacanciesState.Empty(
                                        message = resourceInteractor.getErrorEmptyListVacancy()
                                    ),
                                )
                            }

                            else -> {
                                renderState(
                                    VacanciesState.Content(
                                        tracks = vacancies,
                                    )
                                )
                            }
                        }

                    }
            }
        }
    }

    private fun renderState(state: VacanciesState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
