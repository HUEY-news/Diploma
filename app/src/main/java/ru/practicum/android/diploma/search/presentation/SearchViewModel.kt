package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.search.presentation.model.VacanciesState
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.util.Constants.PAGE
import ru.practicum.android.diploma.util.Constants.PER_PAGE
import ru.practicum.android.diploma.util.Constants.VACANCIES_PER_PAGE
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val resourceInteractor: ResourceInteractor,
    private val searchInteractor: SearchInteractor,
) :
    ViewModel() {
    var lastText: String = ""
    private var currentPage = 0
    private var maxPages = 0
    var totalVacanciesCount: Int = 0

    private val options: HashMap<String, String> = HashMap()

    private fun setOption() {
        maxPages = totalVacanciesCount / VACANCIES_PER_PAGE + 1
        if (totalVacanciesCount > VACANCIES_PER_PAGE && currentPage < maxPages) {
            options[PAGE] = currentPage.toString()
            options[PER_PAGE] = VACANCIES_PER_PAGE.toString()
        }
    }

    private val stateLiveData = MutableLiveData<VacanciesState>()

    fun observeState(): LiveData<VacanciesState> = stateLiveData

    private val trackSearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            searchRequest(changedText)
        }

    fun searchDebounce(changedText: String) {
        renderState(VacanciesState.Loading)
        lastText = changedText
        trackSearchDebounce(changedText)
    }

    private fun updateTotalVacanciesCount(vacancies: List<SimpleVacancy>) {
        totalVacanciesCount = if (vacancies.isNotEmpty()) {
            vacancies[0].found.toInt()
        } else {
            0
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            viewModelScope.launch {
                setOption()
                searchInteractor
                    .searchVacancy(newSearchText, options)
                    .collect { pair ->
                        val vacancies = ArrayList<SimpleVacancy>()
                        if (pair.first != null) {
                            vacancies.addAll(pair.first!!)
                            updateTotalVacanciesCount(vacancies)
                            renderState(
                                VacanciesState.Content(
                                    vacancies = vacancies,
                                )
                            )
                        }
                        when {
                            pair.second != null -> {
                                renderState(
                                    VacanciesState.Error(
                                        errorMessage = pair.second!!
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

                        }
                    }
            }
        }
    }

    private fun renderState(state: VacanciesState) {
        stateLiveData.postValue(state)
    }

    fun uploadData() {
        currentPage++
        searchRequest(lastText)
        renderState(VacanciesState.BottomLoading)
    }

    fun downloadData(request: String) {
        renderState(VacanciesState.Loading)
        searchRequest(request)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
