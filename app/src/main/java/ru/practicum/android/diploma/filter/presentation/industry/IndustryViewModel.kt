package ru.practicum.android.diploma.filter.presentation.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.presentation.model.StateLoadVacancy
import ru.practicum.android.diploma.filter.domain.api.SearchIndustriesInteractor
import ru.practicum.android.diploma.filter.domain.model.Industry
import ru.practicum.android.diploma.filter.presentation.industry.model.IndustriesState

class IndustryViewModel(
    private val searchIndustriesInteractor: SearchIndustriesInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<IndustriesState>()
    private val stateLiveDataNameParameter = MutableLiveData<String>()
    fun observeName(): LiveData<String> = stateLiveDataNameParameter
    fun observeState(): LiveData<IndustriesState> = stateLiveData
    fun searchRequest() {
        viewModelScope.launch {
            StateLoadVacancy.Loading
            searchIndustriesInteractor
                .searchIndustries()
                .collect { pair ->
                    val industries = ArrayList<Industry>()
                    if (pair.first != null) {
                        industries.addAll(pair.first!!)
                        renderState(
                            IndustriesState.Content(
                                industries = industries
                            )
                        )
                    }
                    when {
                        pair.second != null -> {
                            renderState(
                                IndustriesState.Error(
                                    errorMessage = pair.second!!
                                ),
                            )
                        }
                    }
                }
        }
    }

    private fun renderState(state: IndustriesState) {
        stateLiveData.postValue(state)
    }

    fun saveParameterName(name: String) {
        stateLiveDataNameParameter.postValue(name)
    }
}
