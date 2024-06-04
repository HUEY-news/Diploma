package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FiltrationInteractor
import ru.practicum.android.diploma.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.search.domain.model.SimpleVacancy
import ru.practicum.android.diploma.search.presentation.model.VacanciesState
import ru.practicum.android.diploma.sharing.domain.api.ResourceInteractor
import ru.practicum.android.diploma.util.Constants.AREA
import ru.practicum.android.diploma.util.Constants.INDUSTRY
import ru.practicum.android.diploma.util.Constants.ONLY_WITH_SALARY
import ru.practicum.android.diploma.util.Constants.PAGE
import ru.practicum.android.diploma.util.Constants.PER_PAGE
import ru.practicum.android.diploma.util.Constants.SALARY
import ru.practicum.android.diploma.util.Constants.VACANCIES_PER_PAGE
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val resourceInteractor: ResourceInteractor,
    private val searchInteractor: SearchInteractor,
    private val filtrationInteractor: FiltrationInteractor,
) :
    ViewModel() {
    var lastText: String = ""
    private var currentPage = 0
    private var maxPages = 0
    var totalVacanciesCount: Int = 0
    var flagSuccessfulDownload: Boolean = false
    private val options: HashMap<String, String> = HashMap()
    private var flagDebounce = false
    var isNextPageLoading = false

    private fun setOption() {
        val country = filtrationInteractor.getFilter()?.countryId
        val region = filtrationInteractor.getFilter()?.regionId
        val industry = filtrationInteractor.getFilter()?.industryId
        val salary = filtrationInteractor.getFilter()?.expectedSalary
        val onlyWithSalary = filtrationInteractor.getFilter()?.isOnlyWithSalary
        maxPages = totalVacanciesCount / VACANCIES_PER_PAGE + 1
        if (totalVacanciesCount > VACANCIES_PER_PAGE && currentPage < maxPages) {
            options[PAGE] = currentPage.toString()
            options[PER_PAGE] = VACANCIES_PER_PAGE.toString()
        }
        if (country != null) {
            options[AREA] = country
        }
        if (region != null) {
            options[AREA] = region
        }
        if (industry != null) {
            options[INDUSTRY] = industry
        }
        if (salary != null) {
            options[SALARY] = salary.toString()
        }
        if (onlyWithSalary != null && onlyWithSalary != false) {
            options[ONLY_WITH_SALARY] = onlyWithSalary.toString()
        }
    }

    private val stateLiveData = MutableLiveData<VacanciesState>()

    fun observeState(): LiveData<VacanciesState> = stateLiveData

    private val vacancySearchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changedText ->
            run {
                SearchViewModel
                renderState(VacanciesState.Loading)
                searchRequest(changedText)
            }
        }

    fun searchDebounce(changedText: String) {
        if (changedText.trim().isEmpty()) {
            renderState(VacanciesState.Empty(message = resourceInteractor.getErrorEmptyListVacancy()))
            return
        }
        if (lastText != changedText) {
            currentPage = 0
            lastText = changedText
            flagDebounce = true
            vacancySearchDebounce(changedText)
        }
    }

    private fun updateTotalVacanciesCount(vacancies: List<SimpleVacancy>) {
        totalVacanciesCount = if (vacancies.isNotEmpty()) {
            vacancies[0].found.toInt()
        } else {
            0
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.trim().isEmpty()) {
            renderState(VacanciesState.Empty(message = resourceInteractor.getErrorEmptyListVacancy()))
            return
        }
        lastText = newSearchText
        viewModelScope.launch {
            setOption()
            searchInteractor
                .searchVacancy(newSearchText, options)
                .collect { pair ->
                    val vacancies = ArrayList<SimpleVacancy>()
                    if (pair.first != null) {
                        setContent(vacancies)
                        vacancies.addAll(pair.first!!)
                        updateTotalVacanciesCount(vacancies)
                    }
                    when {
                        pair.second != null -> {
                            flagSuccessfulDownload = false
                            renderState(
                                VacanciesState.Error(
                                    errorMessage = pair.second!!
                                ),
                            )
                        }

                        vacancies.isEmpty() -> {
                            flagSuccessfulDownload = false
                            renderState(
                                VacanciesState.Empty(
                                    message = resourceInteractor.getErrorEmptyListVacancy()
                                ),
                            )
                        }
                    }
                }
            isNextPageLoading = false
        }
    }

    private fun setContent(vacancies: ArrayList<SimpleVacancy>) {
        flagSuccessfulDownload = true
        renderState(
            VacanciesState.Content(
                vacancies = vacancies,
            )
        )
    }

    private fun renderState(state: VacanciesState) {
        stateLiveData.postValue(state)
    }

    fun uploadData() {
        if (resourceInteractor.checkInternetConnection()) {
            maxPages = totalVacanciesCount / VACANCIES_PER_PAGE + 1
            if (currentPage < maxPages && !isNextPageLoading) {
                isNextPageLoading = true
                currentPage++
                searchRequest(lastText)
                renderState(VacanciesState.BottomLoading)
            }
        } else {
            renderState(VacanciesState.ErrorToast(errorMessage = resourceInteractor.getErrorInternetConnection()))
        }
    }

    fun downloadData(request: String) {
        if (!flagDebounce) {
            renderState(VacanciesState.Loading)
            searchRequest(request)
        }
    }

    fun saveText(inputTextFromSearch: String) {
        resourceInteractor.addToShared(inputTextFromSearch)
    }

    fun getText(): String {
        return resourceInteractor.getShared()
    }

    fun clearText() {
        resourceInteractor.clearShared()
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}
