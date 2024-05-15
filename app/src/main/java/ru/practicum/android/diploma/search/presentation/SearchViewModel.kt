package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.search.presentation.model.VacanciesState
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val resourceInteractor: ResourceInteractor,
    private val searchInteractor: SearchInteractor,
) :
    ViewModel() {

    private var currentPage = 1
    private var maxPages = 0
    private val vacanciesList = mutableListOf<SimpleVacancy>()

    private val stateLiveData = MutableLiveData<VacanciesState>()

    fun observeState(): LiveData<VacanciesState> = stateLiveData

    private val trackSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchRequest(changedText)
        }

    fun searchDebounce(changedText: String) {
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
                                        vacancies = vacancies,
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

    fun searchVacancies() {
        viewModelScope.launch {
            val searchRequest = "your_search_query_here" // Укажи здесь свой поисковый запрос...
            val options = hashMapOf("page" to currentPage.toString()) // Параметры для пагинации...
            val result = searchInteractor.searchVacancy(searchRequest, options).first()

            if (result.second != null) {
                // Обработка ошибки, если есть...
                return@launch
            }

            result.first?.let { newVacancies ->
                vacanciesList.addAll(newVacancies)
                currentPage++
                if (currentPage <= maxPages) {
                    // Рекурсивный вызов для следующей страницы, если нужно...
                    searchVacancies()
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
